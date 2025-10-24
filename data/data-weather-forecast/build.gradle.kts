plugins {
  alias(libs.plugins.kotlin.multiplatform)
  alias(libs.plugins.android.kotlin.multiplatform.library)
  alias(libs.plugins.kotlin.serialization)
}

kotlin {

  androidLibrary {
    namespace = "gi.aera.weather.data.forecast"
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

  val xcfName = "weather-forecastKit"

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

  sourceSets {
    commonMain {
      dependencies {
        implementation(project(":network"))
        implementation(project(":common"))

        implementation(project(":data-app-settings"))

        implementation(libs.kotlin.stdlib)

        implementation(libs.ktor.client.core)
        implementation(libs.ktor.client.resources)
        implementation(libs.ktor.client.content.negotiation)
        implementation(libs.ktor.serialization.kotlinx.json)

        implementation(libs.koin.core)
        implementation(libs.koin.test)
      }
    }

    commonTest {
      dependencies {
        implementation(libs.kotlin.test)
      }
    }

    androidMain {
      dependencies {

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

      }
    }
  }

}
