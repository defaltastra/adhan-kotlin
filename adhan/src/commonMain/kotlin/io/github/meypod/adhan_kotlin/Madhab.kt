package io.github.meypod.adhan_kotlin

import io.github.meypod.adhan_kotlin.internal.ShadowLength
import io.github.meypod.adhan_kotlin.internal.ShadowLength.DOUBLE
import io.github.meypod.adhan_kotlin.internal.ShadowLength.SINGLE
import kotlinx.serialization.Serializable

/**
 * Madhab for determining how Asr is calculated
 */
@Serializable
enum class Madhab {
  /**
   * Shafi Madhab
   */
  SHAFI,

  /**
   * Hanafi Madhab
   */
  HANAFI;

  val shadowLength: ShadowLength
    get() = when (this) {
      SHAFI -> SINGLE
      HANAFI -> DOUBLE
    }
}
