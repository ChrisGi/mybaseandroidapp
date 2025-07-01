package gi.aera.weather.feature.forecast.domain

import gi.aera.ui.text.UiString

data class Forecast(
  val currentTemperature: String,
  val condition: UiString,
  val conditionIcon: String,
  val weekday: String,
  val location: UiString,
)
