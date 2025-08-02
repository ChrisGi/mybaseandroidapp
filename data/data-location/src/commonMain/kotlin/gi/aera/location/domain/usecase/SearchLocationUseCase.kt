package gi.aera.location.domain.usecase

import gi.aera.location.data.SearchLocationRepository
import gi.aera.location.data.SearchResult
import gi.aera.location.domain.model.SearchLocation
import gi.aera.network.di.domain.map
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class SearchLocationUseCase internal constructor(
  private val searchLocationRepository: SearchLocationRepository,
  private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) {

  suspend operator fun invoke(query: String) = withContext(dispatcher) {
    searchLocationRepository.searchLocation(query)
      .map { response ->
        response.results
          .map { it.toDomain() }
      }
  }
}

private fun SearchResult.toDomain(): SearchLocation {
  return SearchLocation(placeId, city, formatted, lat, lon)
}
