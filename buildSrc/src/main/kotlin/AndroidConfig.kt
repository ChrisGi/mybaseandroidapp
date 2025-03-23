import org.gradle.api.JavaVersion

object AndroidConfig {
  const val APPLICATION_ID = "gi.aera.android"
  const val MIN_SDK = 24
  const val TARGET_SDK = 35
  const val COMPILE_SDK = 35

  const val JVM_TARGET = "17"
  val jvmVersion = JavaVersion.VERSION_17
}