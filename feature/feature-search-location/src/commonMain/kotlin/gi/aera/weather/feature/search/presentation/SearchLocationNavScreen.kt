package gi.aera.weather.feature.search.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import gi.aera.ui.LceViewState
import gi.aera.weather.feature.location.domain.WeatherLocationEvent
import gi.aera.weather.feature.location.presentation.WeatherLocationBottomSheet
import gi.aera.weather.feature.location.presentation.WeatherLocationList
import gi.aera.weather.feature.location.presentation.WeatherLocationViewModel
import gi.aera.weather.feature.search.domain.model.SearchLocationEffect
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel

@Suppress("LongMethod")
@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.searchLocationScreen() {
  composable<SearchLocationNavScreen> {
    val searchLocationViewModel = koinViewModel<SearchLocationViewModel>()
    val state by searchLocationViewModel.searchLocationViewState.collectAsStateWithLifecycle()

    val weatherLocationViewModel = koinViewModel<WeatherLocationViewModel>()
    val weatherLocationState by weatherLocationViewModel.weatherLocationState.collectAsStateWithLifecycle()
    val savedLocationsWeatherState by weatherLocationViewModel.savedLocationsWeatherState.collectAsStateWithLifecycle()

    var showLocationWeather by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
      searchLocationViewModel.searchLocationEffect.collect { effect ->
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
            weatherLocationViewModel.obtainEvent(WeatherLocationEvent.Save(it))
            searchLocationViewModel.closeSearch()
          },
        )
      }

      SearchLocationScreen(
        state = state,
        event = searchLocationViewModel::obtainEvent,
      ) {
        LceViewState(
          state = savedLocationsWeatherState,
          error = {
            Text(
              text = it.message ?: "Something went wrong",
              modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp),
            )
          },
        ) { data ->
          WeatherLocationList(
            data = data,
            onLocationDelete = {
              weatherLocationViewModel.obtainEvent(WeatherLocationEvent.RemoveLocation(it.searchLocation))
            },
          )
        }
      }
    }
  }
}

@Serializable
data object SearchLocationNavScreen
