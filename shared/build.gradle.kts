plugins {
  alias(libs.plugins.kotlin.multiplatform)
  alias(libs.plugins.android.kotlin.multiplatform.library)
}

kotlin {
  androidLibrary {
    namespace = "gi.aera.shared"
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

  val xcfName = "sharedKit"

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
        api(project(":network"))
        api(project(":ui"))
        api(project(":preferences"))

        api(project(":data-weather-forecast"))
        api(project(":data-location"))
        api(project(":data-app-settings"))

        api(project(":feature-weather-forecast"))
        api(project(":feature-search-location"))
        api(project(":feature-weather-components"))
        api(project(":feature-app-settings"))

        implementation(libs.kotlin.stdlib)

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
