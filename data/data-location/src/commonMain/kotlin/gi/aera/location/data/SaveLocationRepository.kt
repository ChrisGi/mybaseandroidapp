package gi.aera.location.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import gi.aera.location.domain.model.LocationNotFoundException
import gi.aera.location.domain.model.SearchLocation
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json

internal class SaveLocationRepository(private val dataStore: DataStore<Preferences>) {

  private val locationKey = stringPreferencesKey(C.DATA_STORE_LOCATION_KEY)

  fun getLocations() = dataStore.data
    .map { preferences ->
      preferences[locationKey] ?: throw LocationNotFoundException()
    }
    .map { json -> Json.decodeFromString(ListSerializer(SearchLocation.serializer()), json) }

  suspend fun saveLocations(locations: List<SearchLocation>) {
    val locationJson = Json.encodeToString(ListSerializer(SearchLocation.serializer()), locations)
    dataStore.edit { preferences ->
      preferences[locationKey] = locationJson
    }
  }

  suspend fun removeLocation(placeId: String) {
    val location = getLocation(placeId) ?: return
    val locations = getLocations().first()

    if (locations.contains(location).not()) return

    val updatedLocations = locations - location
    saveLocations(updatedLocations)
  }

  private suspend fun getLocation(placeId: String): SearchLocation? {
    val locations = getLocations().first()
    return locations.find { it.placeId == placeId }
  }
}
