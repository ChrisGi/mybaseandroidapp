package weather.feature.forecast.mock

import app.cash.turbine.Turbine
import gi.aera.ui.navigation.NavigationArgs
import gi.aera.ui.navigation.Route
import gi.aera.ui.navigation.SettingType
import gi.aera.ui.navigation.domain.model.NavigationManager
import kotlinx.coroutines.flow.Flow

class FakeNavigatorManager : NavigationManager {
  val fakeNavigationRoute = Turbine<NavigationArgs>()

  override val navigationRoute: Flow<NavigationArgs>
    get() = TODO("Not yet implemented")

  override fun navigateTo(event: NavigationArgs) {
    fakeNavigationRoute.add(event)
  }

  override suspend fun navigateToAsync(event: NavigationArgs) {
    fakeNavigationRoute.add(event)
  }

  override fun navigateUp() {
    fakeNavigationRoute.add(NavigationArgs(Route.Up))
  }

  override fun openSystemSettings(settingType: SettingType) {
    TODO("Not yet implemented")
  }
}
