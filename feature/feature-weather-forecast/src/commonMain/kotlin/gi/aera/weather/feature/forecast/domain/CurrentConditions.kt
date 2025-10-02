package gi.aera.weather.feature.forecast.domain

import gi.aera.ui.text.UiString
import org.jetbrains.compose.resources.DrawableResource

data class CurrentConditions(
  val weatherConditions: WeatherConditions,
  val conditionValues: List<ConditionValue>,
)

data class ConditionValue(
  val icon: DrawableResource,
  val value: UiString,
  val description: UiString,
)
