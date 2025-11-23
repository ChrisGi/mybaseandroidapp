plugins {
  alias(libs.plugins.kotlin.multiplatform)
  alias(libs.plugins.android.kotlin.multiplatform.library)
  alias(libs.plugins.compose.multiplatform)
  alias(libs.plugins.kotlin.compose)
  alias(libs.plugins.kotlin.serialization)
  alias(libs.plugins.mokkery)
}

kotlin {

  androidLibrary {
    namespace = "gi.aera.weather.feature.forecast"
    compileSdk = 35
    minSdk = 24

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
        implementation(project(":common"))
        implementation(project(":ui"))

        implementation(project(":data-weather-forecast"))

        implementation(project(":domain-app-settings"))
        implementation(project(":domain-location"))
        implementation(project(":domain-weather-forecast"))

        implementation(project(":feature-shared-components"))
        implementation(project(":feature-app-settings"))

        implementation(libs.kotlin.stdlib)

        implementation(libs.coil.svg)
        implementation(libs.coil.compose)
        implementation(libs.koin.core)
        implementation(libs.koin.compose)
        implementation(libs.koin.compose.viewmodel)
        implementation(libs.koin.compose.viewmodel.navigation)
        implementation(libs.androidx.navigation.compose)
        implementation(libs.androidx.lifecycle.runtimeCompose)
        implementation(libs.kotlinx.datetime)

        implementation(compose.runtime)
        implementation(compose.foundation)
        implementation(compose.material3)
        implementation(compose.ui)
        implementation(compose.components.resources)
        implementation(compose.materialIconsExtended)

        implementation(libs.permissions.location)
        implementation(libs.permissions.compose)

        implementation(libs.koin.test)
      }
    }
    androidMain {
      dependencies {
        implementation(libs.androidx.activity.compose)
        implementation(libs.androidx.ui.tooling)
        implementation(libs.androidx.ui.tooling.preview)
      }
    }
    commonTest {
      dependencies {
        implementation(libs.kotlin.test)
        implementation(libs.kotlinx.coroutines.test)
        implementation(libs.koin.test)
        implementation(libs.koin.core)
        implementation(libs.turbine)

        implementation(project(":ui"))

        implementation(project(":data-app-settings"))
        implementation(project(":data-location"))
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
