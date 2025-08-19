package gi.aera.weather.feature.forecast.domain

import gi.aera.appsettings.domain.model.UnitSystem
import gi.aera.ui.text.UiString

data class WeatherConditions(
  val temperature: String,
  val conditionTitle: UiString,
  val conditionIcon: String,
  val weekday: String,
  val location: UiString,
  val unitSystem: UnitSystem,
)
