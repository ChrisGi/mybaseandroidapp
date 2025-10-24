import com.codingfeline.buildkonfig.compiler.FieldSpec

plugins {
  alias(libs.plugins.kotlin.multiplatform)
  alias(libs.plugins.android.kotlin.multiplatform.library)
  alias(libs.plugins.kotlin.serialization)
  alias(libs.plugins.build.konfig)
}

buildkonfig {
  packageName = "gi.aera.lib.network"
  objectName = "ApiKeys"
  exposeObjectWithName = "ApiKeys"
  val localProperties = LocalProperties(project)
  defaultConfigs {
    buildConfigField(
      FieldSpec.Type.STRING,
      "tomorrowApiKey",
      localProperties.fromLocalPropertiesFile("tomorrow.apiKey"),
    )
    buildConfigField(
      FieldSpec.Type.STRING,
      "geoapifyApiKey",
      localProperties.fromLocalPropertiesFile("geoapify.apiKey"),
    )
  }
}

kotlin {

  // Target declarations - add or remove as needed below. These define
  // which platforms this KMP module supports.
  // See: https://kotlinlang.org/docs/multiplatform-discover-project.html#targets
  androidLibrary {
    namespace = "gi.aera.lib.network"
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

  sourceSets {
    commonMain {
      dependencies {
        implementation(project(":common"))

        implementation(libs.kotlin.stdlib)

        implementation(libs.ktor.client.core)
        implementation(libs.ktor.client.logging)
        implementation(libs.ktor.client.content.negotiation)
        implementation(libs.ktor.client.encoding)
        implementation(libs.ktor.client.resources)
        implementation(libs.ktor.serialization.kotlinx.json)
        implementation(libs.kotlinx.coroutines.core)
        implementation(libs.koin.core)
      }
    }

    commonTest {
      dependencies {
        implementation(libs.kotlin.test)
      }
    }

    androidMain {
      dependencies {
        implementation(libs.ktor.client.okhttp)
        implementation(libs.kotlinx.coroutines.android)

        implementation(libs.koin.core)
        implementation(libs.kotlinx.serialization.json)
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

tasks.named("assemble") {
  dependsOn("generateBuildKonfig")
}
