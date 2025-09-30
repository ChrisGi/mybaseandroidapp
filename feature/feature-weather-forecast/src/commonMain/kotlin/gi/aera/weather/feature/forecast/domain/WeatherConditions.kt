package gi.aera.weather.feature.forecast.domain

import gi.aera.appsettings.domain.model.UnitSystem
import gi.aera.ui.text.UiString
import org.jetbrains.compose.resources.DrawableResource

data class WeatherConditions(
  val temperature: String,
  val temperatureApparent: UiString,
  val conditionTitle: UiString,
  val conditionIcon: DrawableResource,
  val weekday: String,
  val location: UiString,
  val unitSystem: UnitSystem,
)
