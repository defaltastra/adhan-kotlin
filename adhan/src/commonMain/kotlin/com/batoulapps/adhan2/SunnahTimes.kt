package com.batoulapps.adhan2

import com.batoulapps.adhan2.data.CalendarUtil
import com.batoulapps.adhan2.data.CalendarUtil.add
import com.batoulapps.adhan2.data.CalendarUtil.roundedMinute
import com.batoulapps.adhan2.data.CalendarUtil.toUtcInstant
import com.batoulapps.adhan2.data.DateComponents
import kotlinx.datetime.DateTimeUnit
import kotlin.time.Duration
import kotlin.time.Instant

class SunnahTimes(prayerTimes: PrayerTimes, midnightMethod: MidnightMethod = MidnightMethod.SunsetToFajr) {
  val firstThirdOfTheNight: Instant

  /* The midpoint between Maghrib and Fajr */
  val middleOfTheNight: Instant

  /* The beginning of the last third of the period between Maghrib and Fajr,
     a recommended time to perform Qiyam */
  val lastThirdOfTheNight: Instant

  /* night duration in milliseconds */
  val nightDuration: Duration

  init {
    val currentPrayerTimesDate = CalendarUtil.resolveTime(prayerTimes.dateComponents)
    val tomorrowPrayerTimesDate = add(currentPrayerTimesDate, 1, DateTimeUnit.DAY)
    val tomorrowPrayerTimes = prayerTimes.copy(dateComponents = DateComponents.fromLocalDateTime(tomorrowPrayerTimesDate))

    val dawnTime = when (midnightMethod) {
      MidnightMethod.SunsetToFajr -> tomorrowPrayerTimes.fajr
      MidnightMethod.SunsetToSunrise -> tomorrowPrayerTimes.sunrise
    }

    nightDuration = dawnTime.minus(prayerTimes.sunset)

    val nightDurationInSeconds = nightDuration.inWholeSeconds
    firstThirdOfTheNight = roundedMinute(
      add(prayerTimes.sunset, (nightDurationInSeconds / 3.0).toInt(), DateTimeUnit.SECOND)
    ).toUtcInstant()
    middleOfTheNight = roundedMinute(
      add(prayerTimes.sunset, (nightDurationInSeconds / 2.0).toInt(), DateTimeUnit.SECOND)
    ).toUtcInstant()
    lastThirdOfTheNight = roundedMinute(
      add(
        prayerTimes.sunset,
        (nightDurationInSeconds * (2.0 / 3.0)).toInt(),
        DateTimeUnit.SECOND
      )
    ).toUtcInstant()
  }
}
