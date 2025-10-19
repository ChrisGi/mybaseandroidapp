package gi.aera.location.domain.model

import kotlinx.coroutines.flow.Flow

interface DefaultLocationRepository {

  suspend fun saveLocation(location: SearchLocation)
  fun getLocation(): Flow<SearchLocation>
  suspend fun removeLocation()
}
