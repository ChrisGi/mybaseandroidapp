package gi.aera.weathertomorrow

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import gi.aera.shared.di.appModules
import gi.aera.ui.theme.AppTheme
import gi.aera.ui.theme.appTypography
import gi.aera.weather.feature.forecast.presentation.ForecastNavScreen
import gi.aera.weather.feature.forecast.presentation.forecastScreen
import gi.aera.weather.feature.location.presentation.SearchLocationNavScreen
import gi.aera.weather.feature.location.presentation.searchLocationScreen
import org.koin.compose.KoinApplication

@Composable
fun App(
  isSystemInDarkTheme: Boolean,
) {
  KoinApplication(
    application = {
      koinAppDeclaration.invoke(this)
      modules(appModules)
    },
  ) {
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
        searchLocationScreen {
          navController.navigate(ForecastNavScreen) {
            popUpTo(0) { inclusive = true }
          }
        }
      }
    }
  }
}
