package gi.aera.weather.presentation.model

import gi.aera.appsettings.domain.model.UnitSystem

fun UnitSystem.unitSystemValues() = when (this) {
  UnitSystem.METRIC -> UnitSystemValues.Metric
  UnitSystem.IMPERIAL -> UnitSystemValues.Imperial
}

fun UnitSystem.toTemperatureUnit() = unitSystemValues().temperature
fun uvHealthConcern(uvIndex: Int) = UvIndexRange.from(uvIndex)
