package gi.aera.prefrences

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import okio.Path.Companion.toPath

fun createDataStorePreferences(producePath: () -> String) = PreferenceDataStoreFactory.createWithPath(
  produceFile = { producePath().toPath() },
)

internal const val DATA_STORE_FILE_NAME = "app_prefs.preferences_pb"
