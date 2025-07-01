package gi.aera.location.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class SearchLocation(
  val city: String?,
  val formatted: String?,
  val latitude: Double,
  val longitude: Double,
)
