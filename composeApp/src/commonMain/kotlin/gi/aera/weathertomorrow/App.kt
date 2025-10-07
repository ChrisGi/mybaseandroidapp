package gi.aera.weathertomorrow

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import gi.aera.feature.settings.presentation.AppSettingsNavScreen
import gi.aera.feature.settings.presentation.appSettingsNavScreen
import gi.aera.ui.navigation.NavigationLaunchedEffect
import gi.aera.ui.navigation.NavigationManager
import gi.aera.ui.navigation.Route
import gi.aera.ui.theme.AppTheme
import gi.aera.weathertomorrow.theme.appTypography
import gi.aera.weather.feature.forecast.presentation.forecastNavScreen
import gi.aera.weather.feature.search.presentation.searchLocationNavScreen
import org.koin.compose.KoinContext
import org.koin.compose.koinInject

@Composable
fun App(
  isSystemInDarkTheme: Boolean,
) {
  KoinContext {
    AppTheme(appTypography(), isSystemInDarkTheme) {
      val navigationManager: NavigationManager = koinInject()
      val navController = rememberNavController()

      NavigationLaunchedEffect(navigationManager, navController)

      NavHost(
        navController = navController,
        startDestination = Route.ForecastNavScreen,
      ) {
        forecastNavScreen()
        searchLocationNavScreen(
          onBack = {
            navController.navigate(Route.ForecastNavScreen) {
              popUpTo(0) { inclusive = true }
            }
          },
          showSettingsScreen = {
            navController.navigate(AppSettingsNavScreen)
          },
        )
        appSettingsNavScreen {
          navController.navigateUp()
        }
      }
    }
  }
}
