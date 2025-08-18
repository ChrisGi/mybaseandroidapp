package gi.aera.weather.feature.settings

import gi.aera.weather.feature.settings.presentation.SettingsMenuViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val settingsMenuKoinModule = module {
  viewModelOf(::SettingsMenuViewModel)
}
