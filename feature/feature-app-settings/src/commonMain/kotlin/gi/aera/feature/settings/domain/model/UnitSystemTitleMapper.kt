package gi.aera.feature.settings.domain.model

import gi.aera.appsettings.domain.model.UnitSystem
import gi.aera.weather.Res
import gi.aera.weather.settings_unit_system_imperial
import gi.aera.weather.settings_unit_system_metric

fun UnitSystem.toTitle() = when (this) {
  UnitSystem.METRIC -> Res.string.settings_unit_system_metric
  UnitSystem.IMPERIAL -> Res.string.settings_unit_system_imperial
}
