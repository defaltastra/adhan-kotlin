package io.github.meypod.adhan_kotlin

import kotlinx.serialization.Serializable

@Serializable
enum class Prayer {
  NONE,
  FAJR,
  SUNRISE,
  DHUHR,
  ASR,
  MAGHRIB,
  ISHA
}
