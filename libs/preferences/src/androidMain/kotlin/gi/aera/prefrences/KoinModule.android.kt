package gi.aera.prefrences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual val platformModule = module {
  single {
    createDataStore(androidContext())
  }
}

private fun createDataStore(context: Context): DataStore<Preferences> = createDataStorePreferences(
  producePath = { context.filesDir.resolve(DATA_STORE_FILE_NAME).absolutePath },
)
