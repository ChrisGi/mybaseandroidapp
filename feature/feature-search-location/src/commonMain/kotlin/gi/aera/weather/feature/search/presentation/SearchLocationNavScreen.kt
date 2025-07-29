package gi.aera.weather.feature.search.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import gi.aera.ui.LceState
import gi.aera.weather.feature.location.presentation.WeatherLocationBottomSheet
import gi.aera.weather.feature.location.presentation.WeatherLocationViewModel
import gi.aera.weather.feature.search.domain.model.SearchLocationEffect
import gi.aera.weather.feature.search.domain.model.SearchLocationEvent
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel

fun NavGraphBuilder.searchLocationScreen(
  navigateBack: () -> Unit,
) {
  composable<SearchLocationNavScreen> {
    val viewModel = koinViewModel<SearchLocationViewModel>()
    val state by viewModel.searchLocationViewState.collectAsStateWithLifecycle()

    val weatherLocationViewModel = koinViewModel<WeatherLocationViewModel>()
    val weatherLocationState by weatherLocationViewModel.weatherLocationState.collectAsStateWithLifecycle()
    var showLocationWeather by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
      viewModel.searchLocationEffect.collect { effect ->
        when (effect) {
          is SearchLocationEffect.ShowLocationWeather -> {
            showLocationWeather = true
            weatherLocationViewModel.getWeatherLocation(effect.location)
          }
        }
      }
    }

    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      modifier = Modifier
        .background(MaterialTheme.colorScheme.background)
        .fillMaxSize(),
    ) {
      if (showLocationWeather) {
        WeatherLocationBottomSheet(
          state = weatherLocationState,
          hideBottomSheet = { showLocationWeather = false },
          saveLocation = {
            viewModel.obtainEvent(SearchLocationEvent.Save(it))
            navigateBack()
          },

        )
      }

      SearchLocationScreen(
        state,
        LceState.Error(Exception("Error")),
        viewModel::obtainEvent,
        navigateBack,
      )
    }
  }
}

@Serializable
data object SearchLocationNavScreen
