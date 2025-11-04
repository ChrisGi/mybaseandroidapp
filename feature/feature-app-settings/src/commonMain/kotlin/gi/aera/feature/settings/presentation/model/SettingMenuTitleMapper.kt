package gi.aera.feature.settings.presentation.model

import gi.aera.weather.Res
import gi.aera.weather.settings_menu

fun SettingMenuItemId.toTitle() = when (this) {
  SettingMenuItemId.SETTINGS -> Res.string.settings_menu
}
