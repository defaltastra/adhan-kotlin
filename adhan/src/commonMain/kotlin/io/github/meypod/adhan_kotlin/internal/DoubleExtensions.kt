package io.github.meypod.adhan_kotlin.internal

import kotlin.math.PI

fun Double.toRadians() = this * PI / 180
fun Double.toDegrees() = (this * 180) / PI
