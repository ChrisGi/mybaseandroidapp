package gi.aera.weather.feature.settings.domain

import gi.aera.ui.text.UiString

data class SettingMenuItem(
  val title: UiString,
  val id: SettingMenuItemId,
)

enum class SettingMenuItemId {
  SETTINGS,
}
