package gi.aera.prefrences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.core.module.Module
import org.koin.dsl.module

import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSUserDomainMask
import platform.Foundation.NSFileManager

actual val platformModule: Module = module {
  single {
    createDataStore()
  }
}

@OptIn(ExperimentalForeignApi::class)
private fun createDataStore(): DataStore<Preferences> = createDataStorePreferences(
  producePath = {
    val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
      directory = NSDocumentDirectory,
      inDomain = NSUserDomainMask,
      appropriateForURL = null,
      create = false,
      error = null,
    )
    requireNotNull(documentDirectory).path + "/$DATA_STORE_FILE_NAME"
  },
)
