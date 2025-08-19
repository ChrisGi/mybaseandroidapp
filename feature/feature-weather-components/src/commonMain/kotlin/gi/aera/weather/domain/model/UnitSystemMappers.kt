package gi.aera.weather.domain.model

import gi.aera.appsettings.domain.model.UnitSystem

fun UnitSystem.toTemperatureScale() = when (this) {
  UnitSystem.METRIC -> "C"
  UnitSystem.IMPERIAL -> "F"
}
