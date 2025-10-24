package gi.aera.appsettings.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class UnitsSettings(
  val unit: UnitSystem,
)
