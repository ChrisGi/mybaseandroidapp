plugins {
  alias(libs.plugins.kotlin.multiplatform)
  alias(libs.plugins.kotlin.serialization)
}

kotlin {
  // Declare at least one target
  jvm() // Declares a JVM target
  iosX64()
  iosArm64() // Declares a target that corresponds to 64-bit iPhones
  iosSimulatorArm64()

  sourceSets {
    commonMain {
      dependencies {
        api(project(":common"))

        implementation(libs.kotlin.stdlib)

        api(libs.ktor.serialization.kotlinx.json)
        implementation(libs.koin.core)
      }
    }

    commonTest {
      dependencies {
        implementation(libs.kotlin.test)
      }
    }
  }
}
