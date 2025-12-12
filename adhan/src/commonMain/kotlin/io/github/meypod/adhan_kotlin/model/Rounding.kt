package io.github.meypod.adhan_kotlin.model

import kotlinx.serialization.Serializable

@Serializable
enum class Rounding {
  NEAREST,
  UP,
  NONE
}
