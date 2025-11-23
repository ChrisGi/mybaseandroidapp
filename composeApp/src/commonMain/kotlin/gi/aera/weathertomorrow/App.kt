package gi.aera.weathertomorrow

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import gi.aera.feature.settings.presentation.AppSettingsNavScreen
import gi.aera.feature.settings.presentation.appSettingsNavScreen
import gi.aera.ui.navigation.NavigationLaunchedEffect
import gi.aera.ui.navigation.Route
import gi.aera.ui.navigation.domain.model.NavigationManager
import gi.aera.ui.theme.AppTheme
import gi.aera.weather.feature.forecast.presentation.forecastNavScreen
import gi.aera.weather.feature.search.presentation.searchLocationNavScreen
import gi.aera.weather.theme.appTypography
import org.koin.compose.koinInject

@Composable
fun App(
  isSystemInDarkTheme: Boolean,
) {
  AppTheme(appTypography(), isSystemInDarkTheme) {
    val navigationManager: NavigationManager = koinInject()
    val navController = rememberNavController()

    NavigationLaunchedEffect(navigationManager, navController)

    NavHost(
      navController = navController,
      startDestination = Route.ForecastNavScreen,
    ) {
      forecastNavScreen(
        showSettingsScreen = {
          navController.navigate(AppSettingsNavScreen)
        },
      )
      searchLocationNavScreen(
        onBack = {
          navController.navigate(Route.ForecastNavScreen) {
            popUpTo(0) { inclusive = true }
          }
        },
      )
      appSettingsNavScreen {
        navController.navigateUp()
      }
    }
  }
}
