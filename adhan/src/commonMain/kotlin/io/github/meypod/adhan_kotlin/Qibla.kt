package io.github.meypod.adhan_kotlin

import io.github.meypod.adhan_kotlin.internal.QiblaUtil

class Qibla(coordinates: Coordinates) {
  val direction: Double = QiblaUtil.calculateQiblaDirection(coordinates)

  companion object {
    private val MAKKAH = Coordinates(21.4225241, 39.8261818)
  }
}
