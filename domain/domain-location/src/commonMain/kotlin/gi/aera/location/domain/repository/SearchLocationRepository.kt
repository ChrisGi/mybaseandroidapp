package gi.aera.location.domain.repository

import gi.aera.common.model.ApiResponse
import gi.aera.location.domain.model.LocationSearchResponse

interface SearchLocationRepository {

  suspend fun searchLocation(query: String): ApiResponse<LocationSearchResponse>
}
