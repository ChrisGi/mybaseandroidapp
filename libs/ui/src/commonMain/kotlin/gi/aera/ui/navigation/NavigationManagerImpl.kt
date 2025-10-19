package gi.aera.ui.navigation

import gi.aera.ui.navigation.domain.model.NavigationManager
import gi.aera.ui.navigation.domain.model.SystemNavigation
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class NavigationManagerImpl(
  private val systemSettingNavigation: SystemNavigation,
) : NavigationManager {
  private val _navigationRoute = Channel<NavigationArgs>(Channel.BUFFERED)
  override val navigationRoute = _navigationRoute.receiveAsFlow()

  override fun navigateTo(event: NavigationArgs) {
    _navigationRoute.trySend(event)
  }

  override suspend fun navigateToAsync(event: NavigationArgs) {
    _navigationRoute.send(event)
  }

  override fun navigateUp() {
    _navigationRoute.trySend(NavigationArgs(Route.Up))
  }

  override fun openSystemSettings(settingType: SettingType) {
    systemSettingNavigation.openSystemSettings(settingType)
  }
}
