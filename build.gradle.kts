// Top-level build file where you can add configuration options common to all sub-projects/modules.
import ios.DeployIosAppTask
import ios.IosBuildConfig

plugins {
  alias(libs.plugins.android.application) apply false
  alias(libs.plugins.kotlin.android) apply false
  alias(libs.plugins.kotlin.compose) apply false
  alias(libs.plugins.devtools.ksp) apply false
  alias(libs.plugins.kotlin.multiplatform) apply false
  alias(libs.plugins.android.kotlin.multiplatform.library) apply false
  alias(libs.plugins.kotlin.serialization) apply false
  alias(libs.plugins.compose.multiplatform) apply false
  alias(libs.plugins.detekt.plugin) apply true
  alias(libs.plugins.build.konfig) apply false
}

// Register iOS deployment task
tasks.register<DeployIosAppTask>("deployToIPhone") {
  config.set(IosBuildConfig())
}

dependencies {
  detektPlugins(libs.detekt.formatting)
  detektPlugins(libs.detekt.compose)
}
val srcDirs: Set<File> = fileTree("$projectDir") {
  include("composeApp/src/")
  include("data/**/src/")
  include("feature/**/src/")
  include("libs/**/src/")
  include("shared/src/")
}.files

detekt {
  toolVersion = "1.23.8"
  config.setFrom(file("config/detekt/detekt.yml"))
  buildUponDefaultConfig = true
  source.from(
    srcDirs,
  )
}
