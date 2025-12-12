package com.batoulapps.adhan2.model

import kotlinx.serialization.Serializable

@Serializable
enum class Rounding {
  NEAREST,
  UP,
  NONE
}