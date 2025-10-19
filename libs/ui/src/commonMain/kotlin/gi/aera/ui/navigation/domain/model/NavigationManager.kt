package gi.aera.ui.navigation.domain.model

import gi.aera.ui.navigation.NavigationArgs
import gi.aera.ui.navigation.SettingType
import kotlinx.coroutines.flow.Flow

interface NavigationManager {
  val navigationRoute: Flow<NavigationArgs>

  fun navigateTo(event: NavigationArgs)
  suspend fun navigateToAsync(event: NavigationArgs)
  fun navigateUp()
  fun openSystemSettings(settingType: SettingType)
}
