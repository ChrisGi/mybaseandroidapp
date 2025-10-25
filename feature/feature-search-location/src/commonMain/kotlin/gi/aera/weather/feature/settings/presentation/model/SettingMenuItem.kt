package gi.aera.weather.feature.settings.presentation.model

import gi.aera.ui.text.UiString

data class SettingMenuItem(
  val title: UiString,
  val id: SettingMenuItemId,
)

enum class SettingMenuItemId {
  SETTINGS,
}
