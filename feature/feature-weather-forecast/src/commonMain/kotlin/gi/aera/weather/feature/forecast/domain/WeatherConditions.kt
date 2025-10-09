package gi.aera.weather.feature.forecast.domain

import gi.aera.ui.text.UiString
import org.jetbrains.compose.resources.DrawableResource

data class WeatherConditions(
  val temperature: String,
  val temperatureApparent: UiString,
  val temperatureUnit: String,
  val conditionTitle: UiString,
  val conditionIcon: DrawableResource,
  val moment: String,
  val location: UiString,
)
