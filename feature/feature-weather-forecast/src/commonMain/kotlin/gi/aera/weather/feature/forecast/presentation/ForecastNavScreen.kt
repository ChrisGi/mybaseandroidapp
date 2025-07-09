package gi.aera.weather.feature.forecast.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.icerock.moko.permissions.PermissionsController
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.PermissionsControllerFactory
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import gi.aera.weather.feature.forecast.domain.ForecastScreenViewEffect
import gi.aera.weather.feature.forecast.domain.ForecastScreenViewEvent
import kotlinx.coroutines.flow.collectLatest
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

fun NavGraphBuilder.forecastScreen(
  navigateToSearchLocation: (popUpInclusive: Boolean) -> Unit,
) {
  composable<ForecastNavScreen> {
    val factory: PermissionsControllerFactory = rememberPermissionsControllerFactory()
    val controller: PermissionsController = remember(factory) { factory.createPermissionsController() }

    BindEffect(controller)

    val viewModel: ForecastViewModel = koinViewModel { parametersOf(controller) }

    val currentNavigateToSearchLocation by rememberUpdatedState(navigateToSearchLocation)

    LaunchedEffect(Unit) {
      viewModel.effect.collectLatest {
        when (it) {
          ForecastScreenViewEffect.SearchForLocation -> currentNavigateToSearchLocation(true)
        }
      }
    }

    val state by viewModel.viewState.collectAsStateWithLifecycle()

    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      modifier = Modifier
        .background(MaterialTheme.colorScheme.background)
        .safeContentPadding()
        .fillMaxSize(),
    ) {
      ForecastScreen(
        state = state,
        event = {
          when (it) {
            ForecastScreenViewEvent.NavigateToSearchLocation -> currentNavigateToSearchLocation(false)
          }
        },
      )
    }
  }
}

@Serializable
data object ForecastNavScreen
