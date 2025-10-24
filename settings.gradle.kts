pluginManagement {
  repositories {
    google {
      content {
        includeGroupByRegex("com\\.android.*")
        includeGroupByRegex("com\\.google.*")
        includeGroupByRegex("androidx.*")
      }
    }
    mavenCentral()
    gradlePluginPortal()
  }
}
dependencyResolutionManagement {
  repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
  repositories {
    google()
    mavenCentral()
  }
}

rootProject.name = "WeatherTomorrow"

private fun includeModules(directory: String) {
  file(directory).listFiles()
    ?.filter { it.isDirectory && File(it, "build.gradle.kts").exists() }
    ?.forEach {
      val moduleName = it.name
      include(":$moduleName")
      project(":$moduleName").projectDir = it
    }
}

include(":composeApp")
include(":shared")
includeModules("libs")
includeModules("feature")
includeModules("data")
includeModules("domain")
