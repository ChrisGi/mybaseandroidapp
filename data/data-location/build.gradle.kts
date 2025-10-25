plugins {
  alias(libs.plugins.kotlin.multiplatform)
  alias(libs.plugins.android.kotlin.multiplatform.library)
  alias(libs.plugins.kotlin.serialization)
}

kotlin {

  androidLibrary {
    namespace = "gi.aera.lib.data.location"
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

  val xcfName = "locationKit"

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
        implementation(project(":preferences"))
        implementation(project(":common"))

        implementation(project(":domain-location"))

        implementation(libs.kotlin.stdlib)

        implementation(libs.kotlinx.coroutines.core)

        implementation(libs.ktor.client.core)
        implementation(libs.ktor.client.resources)
        implementation(libs.ktor.client.content.negotiation)
        api(libs.ktor.serialization.kotlinx.json)

        api(libs.koin.core)
      }
    }

    commonTest {
      dependencies {
        implementation(libs.kotlin.test)
      }
    }

    androidMain {
      dependencies {
        implementation(libs.koin.android)

        implementation(libs.play.services.location)
        implementation(libs.kotlinx.coroutines.play.services)
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
