package gi.aera.location.data

import gi.aera.domain.model.ApiResponse
import gi.aera.location.domain.model.SearchLocationRepository

internal class SearchLocationRepositoryImpl(
  private val locationApi: LocationApi,
) : SearchLocationRepository {

  override suspend fun searchLocation(query: String): ApiResponse<LocationSearchResponse> =
    locationApi.searchLocation(query)
}
