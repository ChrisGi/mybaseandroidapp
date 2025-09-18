package gi.aera.ui.navigation

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class NavigationManager(
  private val systemSettingNavigation: SystemSettingNavigation,
) {
  private val _navigationRoute = Channel<NavigationArgs>(Channel.BUFFERED)
  val navigationRoute = _navigationRoute.receiveAsFlow()

  fun navigateTo(event: NavigationArgs) {
    _navigationRoute.trySend(event)
  }

  suspend fun navigateToAsync(event: NavigationArgs) {
    _navigationRoute.send(event)
  }

  fun navigateUp() {
    _navigationRoute.trySend(NavigationArgs(Route.Up))
  }

  fun openSystemSettings(settingType: SettingType) {
    systemSettingNavigation.openSystemSettings(settingType)
  }
}
