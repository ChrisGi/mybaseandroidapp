package gi.aera.location.data

internal class SearchLocationRepository(private val locationApi: LocationApi) {

  suspend fun searchLocation(query: String) = locationApi.searchLocation(query)
}
