package io.github.meypod.adhan_kotlin

import kotlinx.serialization.Serializable

@Serializable
enum class MidnightMethod {
    SunsetToSunrise,
    SunsetToFajr,
}
