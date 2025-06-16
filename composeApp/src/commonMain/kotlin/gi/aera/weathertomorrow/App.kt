package gi.aera.weathertomorrow

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import gi.aera.shared.di.appModules
import gi.aera.weather.feature.forecast.presentation.ForecastNavScreen
import gi.aera.weather.feature.forecast.presentation.forecastScreen
import gi.aera.weather.feature.forecast.theme.AppTheme
import gi.aera.weather.feature.forecast.theme.appTypography
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
        forecastScreen()
      }
    }
  }
}
