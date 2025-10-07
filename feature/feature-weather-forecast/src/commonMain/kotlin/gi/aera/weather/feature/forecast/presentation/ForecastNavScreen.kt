package gi.aera.weather.feature.forecast.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.icerock.moko.permissions.PermissionsController
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.PermissionsControllerFactory
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import gi.aera.ui.navigation.Route
import gi.aera.ui.theme.extraColors
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

fun NavGraphBuilder.forecastNavScreen() {
  composable<Route.ForecastNavScreen> {
    val factory: PermissionsControllerFactory = rememberPermissionsControllerFactory()
    val controller: PermissionsController = remember(factory) { factory.createPermissionsController() }
    BindEffect(controller)

    val viewModel: ForecastViewModel = koinViewModel { parametersOf(controller) }

    val forecastViewState by viewModel.forecastViewState.collectAsStateWithLifecycle()
    val currentWeatherViewState by viewModel.currentWeatherViewState.collectAsStateWithLifecycle()

    Surface {
      Box(
        modifier = Modifier
          .fillMaxSize()
          .background(
            brush = MaterialTheme.extraColors.backgroundGradient,
          ),
      ) {
        ForecastScreen(
          currentWeatherState = currentWeatherViewState,
          forecastState = forecastViewState,
          event = viewModel::obtainEvent,
        )
      }
    }
  }
}
