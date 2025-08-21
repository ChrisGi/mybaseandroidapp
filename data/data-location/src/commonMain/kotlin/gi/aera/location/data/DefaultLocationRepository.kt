package gi.aera.location.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import gi.aera.location.domain.model.LocationNotFoundException
import gi.aera.location.domain.model.SearchLocation
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json

internal class DefaultLocationRepository(private val dataStore: DataStore<Preferences>) {

  private val locationKey = stringPreferencesKey(C.DATA_STORE_DEFAULT_LOCATION_KEY)

  suspend fun saveLocation(location: SearchLocation) {
    val locationJson = Json.encodeToString(SearchLocation.serializer(), location)
    dataStore.edit { preferences ->
      preferences[locationKey] = locationJson
    }
  }

  @Suppress("TooGenericExceptionCaught", "SwallowedException")
  fun getLocation() = dataStore.data
    .map { preferences ->
      preferences[locationKey] ?: throw LocationNotFoundException()
    }
    .map { json ->
      try {
        Json.decodeFromString(SearchLocation.serializer(), json)
      } catch (e: Exception) {
        throw LocationNotFoundException()
      }
    }

  suspend fun removeLocation() {
    dataStore.edit { preferences ->
      preferences[locationKey] = ""
    }
  }
}
