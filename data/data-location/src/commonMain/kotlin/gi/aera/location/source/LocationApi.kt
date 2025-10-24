package gi.aera.location.source

import gi.aera.location.domain.model.LocationSearchResponse
import gi.aera.network.domain.apiRequest
import io.ktor.client.HttpClient
import io.ktor.client.plugins.resources.get

class LocationApi(private val httpClient: HttpClient) {

  suspend fun searchLocation(query: String) = apiRequest<LocationSearchResponse> {
    httpClient.get(LocationResource.SearchLocation(text = query))
  }
}
