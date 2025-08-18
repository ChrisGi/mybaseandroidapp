package gi.aera.weather.feature.settings.domain

import gi.aera.weather.Res
import gi.aera.weather.settings_menu

fun SettingMenuItemId.toTitle() = when (this) {
  SettingMenuItemId.SETTINGS -> Res.string.settings_menu
}
