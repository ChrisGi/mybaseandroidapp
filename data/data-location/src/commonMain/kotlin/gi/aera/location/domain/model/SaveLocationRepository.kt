package gi.aera.location.domain.model

import kotlinx.coroutines.flow.Flow

interface SaveLocationRepository {

  fun getLocations(): Flow<List<SearchLocation>>

  suspend fun saveLocations(locations: List<SearchLocation>)

  suspend fun removeLocation(placeId: String)
}
