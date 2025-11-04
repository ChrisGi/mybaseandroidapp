package gi.aera.weather.feature.forecast.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.icerock.moko.permissions.PermissionsController
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.PermissionsControllerFactory
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import gi.aera.feature.settings.presentation.SettingsDropdown
import gi.aera.feature.settings.presentation.SettingsMenuViewModel
import gi.aera.feature.settings.presentation.model.SettingMenuViewEffect
import gi.aera.ui.navigation.Route
import gi.aera.ui.theme.extraColors
import gi.aera.weather.feature.forecast.presentation.model.ForecastScreenViewEvent
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.forecastNavScreen(
  showSettingsScreen: () -> Unit = {},
) {
  composable<Route.ForecastNavScreen> {
    val factory: PermissionsControllerFactory = rememberPermissionsControllerFactory()
    val controller: PermissionsController = remember(factory) { factory.createPermissionsController() }
    BindEffect(controller)

    val viewModel: ForecastViewModel = koinViewModel { parametersOf(controller) }

    val currentWeatherViewState by viewModel.currentWeatherViewState.collectAsStateWithLifecycle()
    val forecastViewState by viewModel.forecastViewState.collectAsStateWithLifecycle()
    val isRefreshing by viewModel.isRefreshing.collectAsStateWithLifecycle(false)

    val menuSettingsViewModel: SettingsMenuViewModel = koinViewModel()
    val menuSettingsState by menuSettingsViewModel.settingsMenuViewState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
      menuSettingsViewModel.settingsMenuViewEffect.collect { effect ->
        when (effect) {
          SettingMenuViewEffect.ShowSettingsScreen -> showSettingsScreen()
        }
      }
    }

    Surface {
      Scaffold(
        bottomBar = {
          BottomAppBar(
            containerColor = Color.Transparent,
            actions = {
              Spacer(Modifier.weight(1f))
              SettingsDropdown(menuSettingsState.settingsMenuState) { menuSettingsViewModel.obtainMenuClick(it) }
            },
          )
        },
        modifier = Modifier,
      ) { innerPadding ->
        Box(
          modifier = Modifier
            .background(brush = MaterialTheme.extraColors.backgroundGradient)
            .fillMaxSize()
            .padding(innerPadding),
        ) {
          PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = { viewModel.obtainEvent(ForecastScreenViewEvent.Retry) },
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
  }
}
