package gi.aera.weather.feature.location.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionsController
import dev.icerock.moko.permissions.location.LOCATION
import gi.aera.common.model.ApiResponse
import gi.aera.common.model.AppError
import gi.aera.location.domain.model.LocationSource
import gi.aera.location.domain.model.SearchLocation
import gi.aera.location.domain.usecase.GetLastGpsLocationUseCase
import gi.aera.location.domain.usecase.GetSavedLocationUseCase
import gi.aera.location.domain.usecase.RemoveDefaultLocationUseCase
import gi.aera.location.domain.usecase.RemoveSavedLocationUseCase
import gi.aera.location.domain.usecase.SaveDefaultLocationUseCase
import gi.aera.location.domain.usecase.SaveLocationUseCase
import gi.aera.ui.C
import gi.aera.ui.EventHandler
import gi.aera.ui.LceState
import gi.aera.ui.navigation.SettingType
import gi.aera.ui.navigation.domain.model.NavigationManager
import gi.aera.weather.feature.location.presentation.model.WeatherLocationEvent
import gi.aera.weather.feature.location.presentation.model.WeatherLocationState
import gi.aera.weather.feature.location.presentation.model.WeatherLocationStateFactory
import gi.aera.weather.forecast.domain.model.RealtimeWeatherResponse
import gi.aera.weather.forecast.domain.usecase.GetCurrentWeatherUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch

@Suppress("LongParameterList")
class WeatherLocationViewModel(
  private val permissionsController: PermissionsController,
  private val getLastGpsLocationsUseCase: GetLastGpsLocationUseCase,
  private val getSavedLocationsUseCase: GetSavedLocationUseCase,
  private val saveDefaultLocationUseCase: SaveDefaultLocationUseCase,
  private val removeDefaultLocationUseCase: RemoveDefaultLocationUseCase,
  private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
  private val weatherLocationFactory: WeatherLocationStateFactory,
  private val removeLocationUseCase: RemoveSavedLocationUseCase,
  private val saveLocationUseCase: SaveLocationUseCase,
  private val navigationManager: NavigationManager,
) : ViewModel(), EventHandler<WeatherLocationEvent> {

  private val _weatherLocationState = MutableStateFlow<LceState<WeatherLocationState.WeatherLocation>>(LceState.Loading)
  val weatherLocationState = _weatherLocationState.asStateFlow()

  private val _savedLocationsWeatherState =
    MutableStateFlow<LceState<List<WeatherLocationState.WeatherLocation>>>(LceState.Loading)
  val savedLocationsWeatherState = _savedLocationsWeatherState
    .onStart { getLocationsWeather() }
    .stateIn(
      viewModelScope,
      SharingStarted.WhileSubscribed(C.RELOADING_TIMEOUT),
      LceState.Loading,
    )

  override fun obtainEvent(event: WeatherLocationEvent) {
    when (event) {
      is WeatherLocationEvent.RemoveLocation -> removeLocation(event.location)
      is WeatherLocationEvent.Save -> saveLocation(event.location)
      is WeatherLocationEvent.SetAsDefault -> setAsDefaultLocation(event.location)
      is WeatherLocationEvent.RetryGetSavedLocations -> getLocationsWeather()
      is WeatherLocationEvent.NavigateToNetworkSettings -> navigationManager.openSystemSettings(SettingType.NETWORK)
    }
  }

  private fun setAsDefaultLocation(location: SearchLocation) {
    viewModelScope.launch {
      when (location.source) {
        LocationSource.GPS -> removeDefaultLocationUseCase()
          .onFailure { println("Error on removing default location:\n$it") }

        LocationSource.SEARCH -> saveDefaultLocationUseCase(location)
          .onFailure { println("Error on saving default location:\n$it") }
      }
    }
  }

  fun getWeatherLocation(location: SearchLocation) = viewModelScope.launch {
    _weatherLocationState.update { LceState.Loading }

    when (val weatherLocationState = getWeatherForecast(location)) {
      is WeatherLocationState.WeatherLocation ->
        _weatherLocationState.update { LceState.Content(weatherLocationState) }

      is WeatherLocationState.WeatherLocationError ->
        _weatherLocationState.update { LceState.Error(weatherLocationState.appError) }
    }
  }

  private fun getLocationsWeather() = viewModelScope.launch {
    _savedLocationsWeatherState.update { LceState.Loading }

    getSavedLocations().zip(getLastLocation()) { savedLocations, lastLocation ->
      listOf(lastLocation)
        .plus(savedLocations)
        .filterNotNull()
    }
      .transform {
        val weatherLocations = it.map { location -> getWeatherForecast(location) }
        emit(weatherLocations)
      }
      .catch { e -> e.printStackTrace() }
      .collect { weatherLocations ->
        when {
          weatherLocations.any { it is WeatherLocationState.WeatherLocationError } ->
            _savedLocationsWeatherState.update {
              val error = weatherLocations.filterIsInstance<WeatherLocationState.WeatherLocationError>().first()
              LceState.Error(error.appError)
            }

          else -> _savedLocationsWeatherState.update {
            LceState.Content(weatherLocations.filterIsInstance<WeatherLocationState.WeatherLocation>())
          }
        }
      }
  }

  private fun getLastLocation() = flow {
    if (permissionsController.isPermissionGranted(Permission.LOCATION)) {
      emit(getLastGpsLocationsUseCase())
    } else {
      try {
        permissionsController.providePermission(Permission.LOCATION)

        emit(getLastGpsLocationsUseCase())
      } catch (_: Exception) {
        emit(null)
      }
    }
  }.catch { emit(null) }

  private fun getSavedLocations() = getSavedLocationsUseCase()
    .catch { emit(emptyList()) }

  private fun removeLocation(location: SearchLocation) = viewModelScope.launch {
    removeLocationUseCase(location)
  }

  private fun saveLocation(location: SearchLocation) = viewModelScope.launch {
    saveLocationUseCase(location)
      .onSuccess { getLocationsWeather() }
      .onFailure { println(it) }
  }

  private suspend fun getWeatherForecast(location: SearchLocation): WeatherLocationState {
    return when (val response = getCurrentWeatherUseCase(location = "${location.latitude}, ${location.longitude}")) {
      is ApiResponse.Error -> {
        WeatherLocationState.WeatherLocationError(AppError.from(response))
      }

      is ApiResponse.Success<RealtimeWeatherResponse> -> weatherLocationFactory.createState(response.data, location)
    }
  }
}
