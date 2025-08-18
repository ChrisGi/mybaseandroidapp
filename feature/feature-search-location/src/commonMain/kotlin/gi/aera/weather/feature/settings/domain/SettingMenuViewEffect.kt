package gi.aera.weather.feature.settings.domain

import gi.aera.ui.Effect

sealed interface SettingMenuViewEffect : Effect {
  data object ShowSettingsScreen : SettingMenuViewEffect
}
