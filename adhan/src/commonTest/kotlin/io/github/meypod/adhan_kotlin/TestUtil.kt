package io.github.meypod.adhan_kotlin

import okio.FileSystem

expect class TestUtil() {
  fun fileSystem(): FileSystem?
  fun environmentVariable(name: String): String?
}
