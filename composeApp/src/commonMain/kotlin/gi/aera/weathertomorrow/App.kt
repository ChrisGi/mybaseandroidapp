package gi.aera.weathertomorrow

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import gi.aera.shared.di.appModules
import gi.aera.weather.feature.forecast.presentation.ForecastNavScreen
import gi.aera.weather.feature.forecast.presentation.ForecastScreen
import gi.aera.weather.feature.forecast.presentation.ForecastViewModel
import gi.aera.weather.feature.forecast.presentation.forecastScreen
import gi.aera.weather.feature.forecast.theme.AppTheme
import gi.aera.weather.feature.forecast.theme.AppTypography
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App(
  isSystemInDarkTheme: Boolean
) {
  KoinApplication(application = {
    modules(appModules)
  }) {
    AppTheme(isSystemInDarkTheme, AppTypography()) {
      val navController = rememberNavController()
      NavHost(
        navController = navController,
        startDestination = ForecastNavScreen
      ) {
        forecastScreen()
      }
    }
  }
}