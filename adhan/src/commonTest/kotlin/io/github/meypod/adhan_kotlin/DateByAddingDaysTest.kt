package io.github.meypod.adhan_kotlin

import io.github.meypod.adhan_kotlin.data.CalendarUtil
import io.github.meypod.adhan_kotlin.data.CalendarUtil.toUtcInstant
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Instant

class DateByAddingDaysTest {

  private fun dateByAddingDays(instant: Instant, days: Int): Instant {
    return CalendarUtil.add(instant, days, DateTimeUnit.DAY).toUtcInstant()
  }

  private fun dayOfMonthAtZone(instant: Instant, zone: TimeZone): Int {
    return instant.toLocalDateTime(zone).dayOfMonth
  }

  private fun dayOfMonthDefaultZone(instant: Instant): Int {
    return instant.toLocalDateTime(TimeZone.currentSystemDefault()).dayOfMonth
  }

  @Test
  fun testAddingDaysNormalDate() {
    val utc = TimeZone.UTC
    val date1 = LocalDateTime(2015, 11, 1, 0, 0, 0).toInstant(utc)
    assertEquals(1, dayOfMonthAtZone(date1, utc))

    val date2 = dateByAddingDays(date1, 1)
    assertEquals(2, dayOfMonthAtZone(date2, utc))

    val date3 = dateByAddingDays(date1, -1)
    assertEquals(31, dayOfMonthAtZone(date3, utc))
  }

  @Test
  fun testAddingDaysNormalDateDefaultZone() {
    val localInstant = LocalDateTime(2015, 11, 1, 0, 0, 0).toInstant(TimeZone.currentSystemDefault())
    assertEquals(1, dayOfMonthDefaultZone(localInstant))

    val plus = dateByAddingDays(localInstant, 1)
    assertEquals(2, dayOfMonthDefaultZone(plus))

    val minus = dateByAddingDays(localInstant, -1)
    assertEquals(31, dayOfMonthDefaultZone(minus))
  }

  @Test
  fun testAddingDaysBrazilEastCaseOne() {
    val brazilEast = TimeZone.of("America/Sao_Paulo")
    val date1 = Instant.fromEpochMilliseconds(1667617200000)
    assertEquals(5, dayOfMonthAtZone(date1, brazilEast))

    val date2 = dateByAddingDays(date1, 1)
    assertEquals(6, dayOfMonthAtZone(date2, brazilEast))

    val date3 = dateByAddingDays(date1, -1)
    assertEquals(4, dayOfMonthAtZone(date3, brazilEast))
  }

  @Test
  fun testAddingDaysBrazilEastCaseOneDefaultZone() {
    val date1 = Instant.fromEpochMilliseconds(1667617200000)
    assertEquals(5, dayOfMonthDefaultZone(date1))

    val date2 = dateByAddingDays(date1, 1)
    assertEquals(6, dayOfMonthDefaultZone(date2))

    val date3 = dateByAddingDays(date1, -1)
    assertEquals(4, dayOfMonthDefaultZone(date3))
  }

  @Test
  fun testAddingDaysBrazilEastCaseTwo() {
    val brazilEast = TimeZone.of("America/Sao_Paulo")
    val date1 = Instant.fromEpochMilliseconds(1699066800000)
    assertEquals(4, dayOfMonthAtZone(date1, brazilEast))

    val date2 = dateByAddingDays(date1, 1)
    assertEquals(5, dayOfMonthAtZone(date2, brazilEast))

    val date3 = dateByAddingDays(date1, -1)
    assertEquals(3, dayOfMonthAtZone(date3, brazilEast))
  }

  @Test
  fun testAddingDaysBrazilEastCaseTwoDefaultZone() {
    val date1 = Instant.fromEpochMilliseconds(1699066800000)
    assertEquals(4, dayOfMonthDefaultZone(date1))

    val date2 = dateByAddingDays(date1, 1)
    assertEquals(5, dayOfMonthDefaultZone(date2))

    val date3 = dateByAddingDays(date1, -1)
    assertEquals(3, dayOfMonthDefaultZone(date3))
  }

  @Test
  fun testAddingDaysTehran() {
    val tehran = TimeZone.of("Asia/Tehran")
    val date1 = Instant.fromEpochMilliseconds(1679257800000)
    assertEquals(20, dayOfMonthAtZone(date1, tehran))

    val date2 = dateByAddingDays(date1, 1)
    assertEquals(21, dayOfMonthAtZone(date2, tehran))

    val date3 = dateByAddingDays(date1, -1)
    assertEquals(19, dayOfMonthAtZone(date3, tehran))
  }

  @Test
  fun testAddingDaysTehranDefaultZone() {
    val date1 = Instant.fromEpochMilliseconds(1679257800000)
    assertEquals(20, dayOfMonthDefaultZone(date1))

    val date2 = dateByAddingDays(date1, 1)
    assertEquals(21, dayOfMonthDefaultZone(date2))

    val date3 = dateByAddingDays(date1, -1)
    assertEquals(19, dayOfMonthDefaultZone(date3))
  }
}
