package gi.aera.location.domain.repository

import gi.aera.location.domain.model.SearchLocation
import kotlinx.coroutines.flow.Flow

interface LocationRepository {

  fun getLocations(): Flow<List<SearchLocation>>

  suspend fun saveLocations(locations: List<SearchLocation>)

  suspend fun removeLocation(placeId: String)
}
