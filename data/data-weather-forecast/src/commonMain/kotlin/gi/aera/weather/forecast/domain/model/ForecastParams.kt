package gi.aera.weather.forecast.domain.model

import gi.aera.appsettings.domain.model.UnitSystem

data class ForecastParams(
  val location: String,
  private val units: UnitSystem,
) {

  val forecastUnits: String
    get() = units.name.lowercase()
}
