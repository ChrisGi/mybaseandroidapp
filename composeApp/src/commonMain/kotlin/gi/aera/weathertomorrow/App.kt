package gi.aera.weathertomorrow

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import gi.aera.feature.settings.presentation.AppSettingsNavScreen
import gi.aera.feature.settings.presentation.appSettingsNavScreen
import gi.aera.ui.theme.AppTheme
import gi.aera.ui.theme.appTypography
import gi.aera.weather.feature.forecast.presentation.ForecastNavScreen
import gi.aera.weather.feature.forecast.presentation.forecastScreen
import gi.aera.weather.feature.search.presentation.SearchLocationNavScreen
import gi.aera.weather.feature.search.presentation.searchLocationScreen
import org.koin.compose.KoinContext

@Composable
fun App(
  isSystemInDarkTheme: Boolean,
) {
  KoinContext {
    AppTheme(appTypography(), isSystemInDarkTheme) {
      val navController = rememberNavController()
      NavHost(
        navController = navController,
        startDestination = ForecastNavScreen,
      ) {
        forecastScreen { popUpInclusive ->
          navController.navigate(SearchLocationNavScreen) {
            if (popUpInclusive) {
              popUpTo(0) { inclusive = true }
            }
          }
        }
        searchLocationScreen(
          onBack = {
            navController.navigate(ForecastNavScreen) {
              popUpTo(0) { inclusive = true }
            }
          },
          showSettingsScreen = {
            navController.navigate(AppSettingsNavScreen)
          },
        )
        appSettingsNavScreen {
          navController.popBackStack()
        }
      }
    }
  }
}
