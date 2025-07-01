package gi.aera.prefrences

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual val preferencesPlatformModule = module {
  single {
    val androidPath = androidContext().filesDir.resolve(DATA_STORE_FILE_NAME).absolutePath
    createDataStorePreferences { androidPath }
  }
}
