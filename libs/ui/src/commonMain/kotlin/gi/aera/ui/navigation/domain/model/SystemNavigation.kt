package gi.aera.ui.navigation.domain.model

import gi.aera.ui.navigation.SettingType

interface SystemNavigation {
  fun openSystemSettings(settingType: SettingType)
}
