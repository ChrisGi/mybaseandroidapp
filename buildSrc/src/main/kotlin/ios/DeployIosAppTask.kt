package ios

import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.TaskAction
import java.io.ByteArrayOutputStream

/**
 * Gradle task to build and deploy iOS app to a physical iPhone device
 */
abstract class DeployIosAppTask : DefaultTask() {

  @get:Input
  abstract val config: Property<IosBuildConfig>

  @get:Input
  @get:Optional
  abstract val deviceId: Property<String>

  init {
    group = "ios"
    description = "Build and deploy iOS app to connected iPhone (auto-detects device or use -PiosDeviceId=xxx)"
  }

  @TaskAction
  fun deploy() {
    val cfg = config.get()
    val devId = getDeviceId(cfg)

    println("📱 Building iOS app for iPhone...")
    println("   Device: $devId")

    buildApp(cfg, devId)
    val appPath = findBuiltApp()
    installApp(devId, appPath)
    launchApp(devId, cfg.bundleId)

    println("✅ App deployed and launched successfully on iPhone!")
  }

  private fun getDeviceId(cfg: IosBuildConfig): String {
    return if (project.hasProperty("iosDeviceId")) {
      project.property("iosDeviceId").toString()
    } else if (deviceId.isPresent) {
      deviceId.get()
    } else {
      autoDetectDevice(cfg)
    }
  }

  private fun autoDetectDevice(cfg: IosBuildConfig): String {
    println("🔍 Auto-detecting connected iPhone...")
    val projectPath = project.file(cfg.projectPath).absolutePath
    val output = ByteArrayOutputStream()

    project.exec {
      commandLine(
        "xcodebuild",
        "-project", projectPath,
        "-scheme", cfg.scheme,
        "-showdestinations",
      )
      standardOutput = output
      isIgnoreExitValue = true
    }

    // Parse line like: { platform:iOS, arch:arm64, id:00008110-000929380169801E, name:iPhone (Krzysztof) }
    val deviceLine = output.toString().lines()
      .find { it.contains("platform:iOS,") && it.contains("id:") && !it.contains("Any iOS Device") }

    if (deviceLine == null) {
      throw GradleException(
        """
        No connected iPhone found!

        Please:
        1. Connect your iPhone via USB cable
        2. Trust this computer on your iPhone
        3. Or specify device ID manually: ./gradlew deployToIPhone -PiosDeviceId=YOUR_DEVICE_ID

        To list devices: xcodebuild -project ${cfg.projectPath} -scheme ${cfg.scheme} -showdestinations
        """.trimIndent(),
      )
    }

    // Extract ID and name from the destination line
    val idMatch = Regex("id:([^,]+)").find(deviceLine)
    val nameMatch = Regex("name:([^}]+)").find(deviceLine)

    if (idMatch == null) {
      throw GradleException("Could not parse device ID from: $deviceLine")
    }

    val parsedDeviceId = idMatch.groupValues[1].trim()
    val deviceName = nameMatch?.groupValues?.get(1)?.trim() ?: "iPhone"
    println("📱 Found device: $deviceName (ID: $parsedDeviceId)")
    return parsedDeviceId
  }

  private fun buildApp(cfg: IosBuildConfig, devId: String) {
    val projectPath = project.file(cfg.projectPath).absolutePath

    try {
      project.exec {
        commandLine(
          "xcodebuild",
          "-project", projectPath,
          "-scheme", cfg.scheme,
          "-configuration", cfg.configuration,
          "-destination", "id=$devId",
          "build",
        )
      }
    } catch (e: Exception) {
      throw GradleException(
        """
        ❌ Build failed!

        This is likely due to one of these issues:
        1. iOS device support not installed for your iPhone's iOS version
           → Open Xcode, press Cmd+Shift+2, select your iPhone, wait for "Preparing device..." to complete

        2. Code signing issue
           → Open the project in Xcode and configure signing in the iosApp target

        3. Device not trusted
           → Check your iPhone for a "Trust this computer?" prompt

        Original error: ${e.message}

        To see detailed error, run in Xcode or check:
        xcodebuild -project $projectPath -scheme ${cfg.scheme} -configuration ${cfg.configuration} -destination 'id=$devId' build
        """.trimIndent(),
        e,
      )
    }
  }

  private fun findBuiltApp(): String {
    println("🔍 Finding built app...")
    val derivedDataDir = project.file(
      System.getProperty("user.home") + "/Library/Developer/Xcode/DerivedData",
    )

    val appPath = derivedDataDir.listFiles()
      ?.find { it.name.startsWith("iosApp-") }
      ?.resolve("Build/Products/Debug-iphoneos/iosApp.app")

    if (appPath == null || !appPath.exists()) {
      throw GradleException("Built app not found at expected location. Build may have failed.")
    }

    return appPath.absolutePath
  }

  private fun installApp(devId: String, appPath: String) {
    println("📲 Installing app on iPhone...")
    project.exec {
      commandLine(
        "xcrun", "devicectl", "device", "install", "app",
        "--device", devId,
        appPath,
      )
    }
  }

  private fun launchApp(devId: String, bundleId: String) {
    println("🚀 Launching app on iPhone...")
    project.exec {
      commandLine(
        "xcrun", "devicectl", "device", "process", "launch",
        "--device", devId,
        bundleId,
      )
    }
  }
}
