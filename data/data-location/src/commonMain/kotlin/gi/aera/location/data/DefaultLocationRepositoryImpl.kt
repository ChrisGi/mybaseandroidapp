package gi.aera.location.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import gi.aera.location.domain.model.LocationNotFoundException
import gi.aera.location.domain.repository.DefaultLocationRepository
import gi.aera.location.domain.model.SearchLocation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json

internal class DefaultLocationRepositoryImpl(private val dataStore: DataStore<Preferences>) : DefaultLocationRepository {

  private val locationKey = stringPreferencesKey(C.DATA_STORE_DEFAULT_LOCATION_KEY)

  override suspend fun saveLocation(location: SearchLocation) {
    val locationJson = Json.encodeToString(SearchLocation.serializer(), location)
    dataStore.edit { preferences ->
      preferences[locationKey] = locationJson
    }
  }

  @Suppress("TooGenericExceptionCaught")
  override fun getLocation(): Flow<SearchLocation> = dataStore.data
    .map { preferences ->
      preferences[locationKey] ?: throw LocationNotFoundException()
    }
    .map { json ->
      try {
        Json.decodeFromString(SearchLocation.serializer(), json)
      } catch (_: Exception) {
        throw LocationNotFoundException()
      }
    }

  override suspend fun removeLocation() {
    dataStore.edit { preferences ->
      preferences[locationKey] = ""
    }
  }
}
