plugins {
  alias(libs.plugins.kotlin.multiplatform)
  alias(libs.plugins.android.kotlin.multiplatform.library)
  alias(libs.plugins.kotlin.serialization)
  alias(libs.plugins.compose.multiplatform)
  alias(libs.plugins.kotlin.compose)
}

kotlin {

  // Target declarations - add or remove as needed below. These define
  // which platforms this KMP module supports.
  // See: https://kotlinlang.org/docs/multiplatform-discover-project.html#targets
  androidLibrary {
    namespace = "gi.aera.lib.ui"
    compileSdk = AndroidConfig.COMPILE_SDK
    minSdk = AndroidConfig.MIN_SDK

    withHostTestBuilder {
    }

    withDeviceTestBuilder {
      sourceSetTreeName = "test"
    }.configure {
      instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
  }

  // For iOS targets, this is also where you should
  // configure native binary output. For more information, see:
  // https://kotlinlang.org/docs/multiplatform-build-native-binaries.html#build-xcframeworks

  // A step-by-step guide on how to include this library in an XCode
  // project can be found here:
  // https://developer.android.com/kotlin/multiplatform/migrate
  val xcfName = "networkKit"

  iosX64 {
    binaries.framework {
      baseName = xcfName
    }
  }

  iosArm64 {
    binaries.framework {
      baseName = xcfName
    }
  }

  iosSimulatorArm64 {
    binaries.framework {
      baseName = xcfName
    }
  }

  // Source set declarations.
  // Declaring a target automatically creates a source set with the same name. By default, the
  // Kotlin Gradle Plugin creates additional source sets that depend on each other, since it is
  // common to share sources between related targets.
  // See: https://kotlinlang.org/docs/multiplatform-hierarchy.html
  sourceSets {
    commonMain {
      dependencies {
        implementation(libs.kotlin.stdlib)

        implementation(libs.ktor.client.core)
        implementation(libs.ktor.client.logging)
        implementation(libs.ktor.client.content.negotiation)
        implementation(libs.ktor.client.encoding)

        implementation(libs.kotlinx.coroutines.core)
        implementation(libs.koin.core)
        implementation(libs.koin.test)

        implementation(compose.runtime)
        implementation(compose.foundation)
        implementation(compose.material3)
        implementation(compose.ui)
        implementation(compose.components.resources)
        implementation(compose.preview)
        implementation(compose.components.uiToolingPreview)
      }
    }

    commonTest {
      dependencies {
        implementation(libs.kotlin.test)
      }
    }

    androidMain {
      dependencies {
        // Add Android-specific dependencies here. Note that this source set depends on
        // commonMain by default and will correctly pull the Android artifacts of any KMP
        // dependencies declared in commonMain.
        implementation(libs.ktor.client.okhttp)
        implementation(libs.kotlinx.coroutines.android)

        implementation(libs.koin.core)
      }
    }

    getByName("androidDeviceTest") {
      dependencies {
        implementation(libs.androidx.runner)
        implementation(libs.androidx.core)
        implementation(libs.androidx.junit)
      }
    }

    iosMain {
      dependencies {
        // Add iOS-specific dependencies here. This a source set created by Kotlin Gradle
        // Plugin (KGP) that each specific iOS target (e.g., iosX64) depends on as
        // part of KMP’s default source set hierarchy. Note that this source set depends
        // on common by default and will correctly pull the iOS artifacts of any
        // KMP dependencies declared in commonMain.

        implementation(libs.ktor.client.ios)
      }
    }
  }
}

compose.resources {
  publicResClass = true
  packageOfResClass = "gi.aera.weather"
  generateResClass = auto
}
