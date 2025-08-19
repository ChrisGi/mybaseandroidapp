package gi.aera.weather.feature.forecast.presentation

import ForecastDailyResponse
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.icerock.moko.permissions.DeniedAlwaysException
import dev.icerock.moko.permissions.DeniedException
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionsController
import dev.icerock.moko.permissions.RequestCanceledException
import dev.icerock.moko.permissions.location.LOCATION
import gi.aera.location.domain.model.PermissionException
import gi.aera.location.domain.model.SearchLocation
import gi.aera.location.domain.usecase.GetDefaultLocationUseCase
import gi.aera.network.di.domain.ApiResponse
import gi.aera.ui.C
import gi.aera.ui.LceState
import gi.aera.weather.feature.forecast.domain.WeatherConditions
import gi.aera.weather.feature.forecast.domain.ForecastScreenViewEffect
import gi.aera.weather.feature.forecast.domain.ForecastViewStateFactory
import gi.aera.weather.feature.forecast.domain.RealtimeWeatherViewStateFactory
import gi.aera.weather.forecast.domain.model.RealtimeWeatherResponse
import gi.aera.weather.forecast.domain.usecase.GetDailyForecastUseCase
import gi.aera.weather.forecast.domain.usecase.GetCurrentWeatherUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

class ForecastViewModel(
  private val permissionsController: PermissionsController,
  private val getDefaultLocationUseCase: GetDefaultLocationUseCase,
  private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
  private val getDailyForecastUseCase: GetDailyForecastUseCase,
  private val realtimeWeatherViewStateFactory: RealtimeWeatherViewStateFactory,
  private val forecastViewStateFactory: ForecastViewStateFactory,
) : ViewModel() {

  private val permission = Permission.LOCATION

  private val _currentWeatherViewstate = MutableStateFlow<LceState<WeatherConditions>>(LceState.Loading)
  val currentWeatherViewState = _currentWeatherViewstate
    .onStart { getCurrentWeatherForLocation() }
    .stateIn(
      viewModelScope,
      SharingStarted.WhileSubscribed(C.RELOADING_TIMEOUT),
      LceState.Loading,
    )

  private val _forecastViewstate = MutableStateFlow<LceState<List<WeatherConditions>>>(LceState.Loading)
  val forecastViewState = _forecastViewstate
    .onStart { getForecastForLocation() }
    .stateIn(
      viewModelScope,
      SharingStarted.WhileSubscribed(C.RELOADING_TIMEOUT),
      LceState.Loading,
    )

  private val _effect = MutableSharedFlow<ForecastScreenViewEffect>()
  val effect = _effect.asSharedFlow()

  private fun getCurrentWeatherForLocation() = viewModelScope.launch {
    getDefaultLocation()
      .collect {
        getCurrentWeather(it)
      }
  }

  private fun getCurrentWeather(location: SearchLocation) = viewModelScope.launch {
    _currentWeatherViewstate.update { LceState.Loading }

    val state = when (
      val response = getCurrentWeatherUseCase(location = "${location.latitude}, ${location.longitude}")
    ) {
      is ApiResponse.Error ->
        LceState.Error(Exception(response.errorMessage))

      is ApiResponse.Success<RealtimeWeatherResponse> ->
        LceState.Content(realtimeWeatherViewStateFactory.createState(response.data, location))
    }
    _currentWeatherViewstate.update { state }
  }

  private fun getForecastForLocation() = viewModelScope.launch {
    getDefaultLocation()
      .collect {
        getForecast(it)
      }
  }

  private fun getDefaultLocation() = getDefaultLocationUseCase()
    .catch { e ->
      when {
        e is PermissionException -> {
          e.printStackTrace() // todo log error to see how often data store exception is thrown
          provideLocationPermission()
          throw CancellationException("Location permission not granted")
        }

        else -> throw CancellationException("General exception")
      }
    }

  private suspend fun getForecast(location: SearchLocation) {
    _forecastViewstate.update { LceState.Loading }

    val state = when (
      val response = getDailyForecastUseCase(location = "${location.latitude}, ${location.longitude}")
    ) {
      is ApiResponse.Error ->
        LceState.Error(Exception(response.errorMessage))

      is ApiResponse.Success<ForecastDailyResponse> ->
        LceState.Content(forecastViewStateFactory.createState(response.data, location))
    }
    _forecastViewstate.update { state }
  }

  private fun provideLocationPermission() {
    viewModelScope.launch {
      try {
        permissionsController.providePermission(permission)

        getForecastForLocation()
      } catch (e: DeniedAlwaysException) {
        e.printStackTrace()
        _effect.emit(ForecastScreenViewEffect.SearchForLocation)
      } catch (e: DeniedException) {
        e.printStackTrace()
        _effect.emit(ForecastScreenViewEffect.SearchForLocation)
      } catch (e: RequestCanceledException) {
        e.printStackTrace()
      }
    }
  }
}
