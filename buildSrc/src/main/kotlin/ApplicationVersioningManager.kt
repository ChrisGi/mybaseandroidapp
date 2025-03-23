import org.gradle.api.Project
import java.util.concurrent.TimeUnit

object ApplicationVersioningManager {

  private const val GIT_COMMAND_TIMEOUT = 10L
  private const val BASE_VERSION_CODE = 100
  private const val BASE_VERSION_NAME = "1.0"

  fun getVersionInfo(project: Project): Pair<Int, String> {
    val gitCommitCount = runCatching {
      val process = Runtime.getRuntime().exec("git rev-list --count HEAD")
      process.waitFor(GIT_COMMAND_TIMEOUT, TimeUnit.SECONDS) // Wait for a maximum of 10 seconds
      process.inputStream.bufferedReader().use { it.readLine().trim().toInt() }
    }.getOrElse {
      println("Error getting git commit count: $it")
      0
    }

    val gitCommitHash = runCatching {
      val process = Runtime.getRuntime().exec("git rev-parse --short HEAD")
      process.waitFor(GIT_COMMAND_TIMEOUT, TimeUnit.SECONDS)
      process.inputStream.bufferedReader().use { it.readLine().trim() }
    }.getOrElse {
      println("Error getting git commit hash: $it")
      gitCommitCount
    }

    val baseVersionCode = BASE_VERSION_CODE
    val baseVersionName = BASE_VERSION_NAME

    val calculatedVersionCode = baseVersionCode + gitCommitCount

    val calculatedVersionName = if (project.gradle.startParameter.taskNames.any { it.contains("Release") }) {
      "$baseVersionName.$gitCommitCount"
    } else {
      "$baseVersionName.$gitCommitHash"
    }


    println("Version Code: $calculatedVersionCode")
    println("Version Name: $calculatedVersionName")

    return calculatedVersionCode to calculatedVersionName
  }
}