package ios

/**
 * Configuration for iOS build and deployment
 */
data class IosBuildConfig(
  val projectPath: String = "iosApp/iosApp.xcodeproj",
  val scheme: String = "iosApp",
  val bundleId: String = "gi.aera.weathertomorrow.ios",
  val configuration: String = "Debug",
)