plugins {
  alias(libs.plugins.kotlin.multiplatform)
  alias(libs.plugins.android.kotlin.multiplatform.library)
  alias(libs.plugins.kotlin.serialization)
}

kotlin {

  androidLibrary {
    namespace = "gi.aera.lib.preferences"
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

  val xcfName = "preferencesKit"

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
        implementation(libs.kotlin.stdlib)

        implementation(libs.kotlinx.coroutines.core)
        implementation(libs.koin.core)
        implementation(libs.koin.test)

        api(libs.androidx.datastore)
        api(libs.androidx.datastore.preferences)
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

        implementation(libs.ktor.client.ios)
      }
    }
  }

}
