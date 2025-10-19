package gi.aera.ui.navigation

import gi.aera.ui.navigation.domain.model.SystemNavigation

expect class SystemSettingNavigation : SystemNavigation {
  override fun openSystemSettings(settingType: SettingType)
}
