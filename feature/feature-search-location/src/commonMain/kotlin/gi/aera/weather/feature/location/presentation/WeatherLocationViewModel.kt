package gi.aera.weather.feature.location.presentation

import ForecastResponseDaily
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gi.aera.location.domain.model.SearchLocation
import gi.aera.location.domain.usecase.GetSavedLocationUseCase
import gi.aera.location.domain.usecase.RemoveSavedLocationUseCase
import gi.aera.location.domain.usecase.SaveLocationUseCase
import gi.aera.network.di.domain.ApiResponse
import gi.aera.ui.C
import gi.aera.ui.EventHandler
import gi.aera.ui.LceState
import gi.aera.weather.feature.location.domain.WeatherLocationEvent
import gi.aera.weather.feature.location.domain.WeatherLocationFactory
import gi.aera.weather.feature.location.domain.WeatherLocationState
import gi.aera.weather.forecast.domain.model.ForecastParams
import gi.aera.weather.forecast.domain.usecase.GetDailyForecastUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WeatherLocationViewModel(
  private val getSavedLocationsUseCase: GetSavedLocationUseCase,
  private val getWeatherForecastUseCase: GetDailyForecastUseCase,
  private val weatherLocationFactory: WeatherLocationFactory,
  private val removeLocationUseCase: RemoveSavedLocationUseCase,
  private val saveLocationUseCase: SaveLocationUseCase,
) : ViewModel(), EventHandler<WeatherLocationEvent> {

  private val _weatherLocationState = MutableStateFlow<LceState<WeatherLocationState.WeatherLocation>>(LceState.Loading)
  val weatherLocationState = _weatherLocationState.asStateFlow()

  private val _savedLocationsWeatherState =
    MutableStateFlow<LceState<List<WeatherLocationState.WeatherLocation>>>(LceState.Loading)
  val savedLocationsWeatherState = _savedLocationsWeatherState
    .onStart { getSavedLocationsWeather() }
    .stateIn(
      viewModelScope,
      SharingStarted.WhileSubscribed(C.RELOADING_TIMEOUT),
      LceState.Loading,
    )

  override fun obtainEvent(event: WeatherLocationEvent) {
    when (event) {
      is WeatherLocationEvent.RemoveLocation -> removeLocation(event.location)
      is WeatherLocationEvent.Save -> saveLocation(event.location)
    }
  }

  fun getWeatherLocation(location: SearchLocation) = viewModelScope.launch {
    _weatherLocationState.update { LceState.Loading }

    when (val weatherLocationState = getWeatherForecast(location)) {
      is WeatherLocationState.WeatherLocation ->
        _weatherLocationState.update { LceState.Content(weatherLocationState) }

      is WeatherLocationState.WeatherLocationError ->
        _weatherLocationState.update { LceState.Error(weatherLocationState) }
    }
  }

  private fun getSavedLocationsWeather() = viewModelScope.launch {
    _savedLocationsWeatherState.update { LceState.Loading }

    val weatherLocations = getSavedLocationsUseCase()
      .catch { emit(emptyList()) }
      .transform {
        val weatherLocations = it.map { location -> getWeatherForecast(location) }
        emit(weatherLocations)
      }
      .first()

    when {
      weatherLocations.any { it is WeatherLocationState.WeatherLocationError } ->
        _savedLocationsWeatherState.update {
          LceState.Error(weatherLocations.filterIsInstance<WeatherLocationState.WeatherLocationError>().first())
        }

      else -> _savedLocationsWeatherState.update {
        LceState.Content(weatherLocations.filterIsInstance<WeatherLocationState.WeatherLocation>())
      }
    }
  }

  private fun removeLocation(location: SearchLocation?) = viewModelScope.launch {
    location?.let { removeLocationUseCase(it) }
  }

  private fun saveLocation(location: SearchLocation) = viewModelScope.launch {
    saveLocationUseCase(location)
      .onSuccess { getSavedLocationsWeather() }
      .onFailure { println(it) }
  }

  private suspend fun getWeatherForecast(location: SearchLocation): WeatherLocationState {
    val params = ForecastParams(location = "${location.latitude}, ${location.longitude}")
    return when (val response = getWeatherForecastUseCase(params)) {
      is ApiResponse.Error -> {
        WeatherLocationState.WeatherLocationError("Something went wrong")
      }

      is ApiResponse.Success<ForecastResponseDaily> -> weatherLocationFactory.createState(response.data, location)
    }
  }
}
