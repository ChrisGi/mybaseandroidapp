package gi.aera.feature.settings

import gi.aera.feature.settings.presentation.UnitSystemSettingViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appSettingsFeatureModule = module {
  viewModelOf(::UnitSystemSettingViewModel)
}
