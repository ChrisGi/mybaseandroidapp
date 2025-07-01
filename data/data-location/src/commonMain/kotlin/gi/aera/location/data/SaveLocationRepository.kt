package gi.aera.location.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import gi.aera.location.domain.model.LocationNotFoundException
import kotlinx.coroutines.flow.map

internal class SaveLocationRepository(private val dataStore: DataStore<Preferences>) {

  private val locationKey = stringPreferencesKey(C.DATA_STORE_LOCATION_KEY)

  suspend fun saveLocation(location: String) {
    dataStore.edit { preferences ->
      preferences[locationKey] = location
    }
  }

  fun getSavedLocation() = dataStore.data
    .map { preferences ->
      preferences[locationKey] ?: throw LocationNotFoundException()
    }
}
