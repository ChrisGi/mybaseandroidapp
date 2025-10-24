package gi.aera.location.source

import io.ktor.resources.Resource

@Resource("/geocode")
internal class LocationResource {
  @Resource("autocomplete")
  class SearchLocation(
    val parent: LocationResource = LocationResource(),
    val text: String,
    val format: String = "json",
    val lang: String = "pl",
  )
}
