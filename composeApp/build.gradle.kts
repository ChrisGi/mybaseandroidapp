import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
  alias(libs.plugins.kotlin.multiplatform)
  alias(libs.plugins.android.application)
  alias(libs.plugins.compose.multiplatform)
  alias(libs.plugins.kotlin.compose)
}

kotlin {
  androidTarget {
    @OptIn(ExperimentalKotlinGradlePluginApi::class)
    compilerOptions {
      jvmTarget.set(JvmTarget.JVM_11)
    }
  }

  listOf(
    iosX64(),
    iosArm64(),
    iosSimulatorArm64(),
  ).forEach { iosTarget ->
    iosTarget.binaries.framework {
      baseName = "Weather Tomorrow"
      isStatic = true
    }
  }

  sourceSets {

    androidMain.dependencies {
      implementation(libs.androidx.activity.compose)
      implementation(libs.slf4j.android)

      implementation(libs.koin.android)
    }
    commonMain.dependencies {
      implementation(project(":network"))
      implementation(project(":ui"))
      implementation(project(":preferences"))
      implementation(project(":common"))

      implementation(project(":data-weather-forecast"))
      implementation(project(":data-location"))
      implementation(project(":data-app-settings"))

      implementation(project(":domain-weather-forecast"))
      implementation(project(":domain-location"))
      implementation(project(":domain-app-settings"))

      implementation(project(":feature-weather-forecast"))
      implementation(project(":feature-search-location"))
      implementation(project(":feature-weather-components"))
      implementation(project(":feature-app-settings"))


      implementation(libs.koin.core)
      implementation(libs.koin.compose)
      implementation(libs.koin.compose.viewmodel)
      implementation(libs.koin.compose.viewmodel.navigation)
      implementation(libs.kotlinx.datetime)

      implementation(compose.runtime)
      implementation(compose.foundation)
      implementation(compose.material3)
      implementation(compose.ui)
      implementation(compose.components.resources)
      implementation(compose.preview)
      implementation(compose.components.uiToolingPreview)

      implementation(libs.androidx.lifecycle.viewmodel)
      implementation(libs.androidx.lifecycle.runtimeCompose)
    }
    commonTest.dependencies {
      implementation(libs.kotlin.test)
    }
  }
}

android {
  namespace = "gi.aera.weathertomorrow"
  compileSdk = AndroidConfig.COMPILE_SDK

  defaultConfig {
    applicationId = AndroidConfig.APPLICATION_ID
    minSdk = AndroidConfig.MIN_SDK
    targetSdk = AndroidConfig.TARGET_SDK

    val (vCode, vName) = ApplicationVersioningManager.getVersionInfo(project)
    versionCode = vCode
    versionName = vName

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }
  packaging {
    resources {
      excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
  }
  buildTypes {
    getByName("release") {
      isMinifyEnabled = false
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
  }
  buildFeatures {
    compose = true
  }
}

dependencies {
  debugImplementation(compose.uiTooling)
}

compose.resources {
  publicResClass = true
  packageOfResClass = "gi.aera.weather"
  generateResClass = always
}
