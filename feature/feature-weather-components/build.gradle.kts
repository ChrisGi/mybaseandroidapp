plugins {
  alias(libs.plugins.kotlin.multiplatform)
  alias(libs.plugins.android.kotlin.multiplatform.library)
  alias(libs.plugins.compose.multiplatform)
  alias(libs.plugins.kotlin.compose)
  alias(libs.plugins.kotlin.serialization)
}

kotlin {

  androidLibrary {
    namespace = "gi.aera.weather.components"
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

  val xcfName = "weather-componentsKit"

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
        api(project(":ui"))

        implementation(libs.kotlin.stdlib)

        implementation(libs.coil.svg)
        implementation(libs.coil.compose)
        implementation(libs.koin.core)
        implementation(libs.koin.compose)
        implementation(libs.androidx.navigation.compose)
        implementation(libs.androidx.lifecycle.runtimeCompose)
        implementation(libs.kotlinx.datetime)

        implementation(compose.runtime)
        implementation(compose.foundation)
        implementation(compose.material3)
        implementation(compose.ui)
        implementation(compose.components.resources)
        implementation(compose.preview)
        implementation(compose.components.uiToolingPreview)
        implementation(compose.materialIconsExtended)

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

compose.resources {
  publicResClass = true
  packageOfResClass = "gi.aera.weather"
  generateResClass = auto
}
