package gi.aera.feature.weather.doubles

import gi.aera.location.domain.model.SearchLocation
import gi.aera.location.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class StubLocationRepositoryImpl : LocationRepository {

  override fun getLocations(): Flow<List<SearchLocation>> = flowOf(listOf(fakeSearchLocation))

  override suspend fun saveLocations(locations: List<SearchLocation>) = Unit

  override suspend fun removeLocation(placeId: String) = Unit
}
