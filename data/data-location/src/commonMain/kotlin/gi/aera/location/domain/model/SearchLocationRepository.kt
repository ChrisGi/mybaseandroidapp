package gi.aera.location.domain.model

import gi.aera.domain.model.ApiResponse
import gi.aera.location.data.LocationSearchResponse

interface SearchLocationRepository {

  suspend fun searchLocation(query: String): ApiResponse<LocationSearchResponse>
}
