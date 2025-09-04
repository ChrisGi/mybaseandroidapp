import org.gradle.api.Project
import java.util.Properties

class LocalProperties(private val project: Project) {

  private val localPropertiesFile by lazy {
    project.rootProject.file("local.properties")
  }
  private val properties: Properties by lazy {
    Properties().apply {
      if (localPropertiesFile.exists()) {
        load(localPropertiesFile.inputStream())
      }
    }
  }

  fun fromLocalPropertiesFile(key: String): String? {
    return properties.getProperty(key)
  }
}
