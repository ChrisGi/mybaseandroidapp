package gi.aera.weather.feature.settings.presentation.model

import gi.aera.ui.Effect

sealed interface SettingMenuViewEffect : Effect {
  data object ShowSettingsScreen : SettingMenuViewEffect
}
