package gi.aera.weather.forecast.domain.model

import gi.aera.appsettings.domain.model.UnitSystem

data class ForecastParams(
  val location: String,
  val units: UnitSystem,
  val forceFreshData: Boolean = false,
)
