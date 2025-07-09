package gi.aera.weather.feature.forecast.presentation

import ForecastResponseDaily
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.icerock.moko.permissions.DeniedAlwaysException
import dev.icerock.moko.permissions.DeniedException
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionsController
import dev.icerock.moko.permissions.RequestCanceledException
import dev.icerock.moko.permissions.location.LOCATION
import gi.aera.location.domain.model.SearchLocation
import gi.aera.location.domain.usecase.GetLastLocationUseCase
import gi.aera.location.domain.usecase.GetSavedLocationUseCase
import gi.aera.network.di.domain.ApiResponse
import gi.aera.ui.C
import gi.aera.ui.LceState
import gi.aera.weather.feature.forecast.domain.Forecast
import gi.aera.weather.feature.forecast.domain.ForecastScreenViewEffect
import gi.aera.weather.feature.forecast.domain.ForecastViewStateFactory
import gi.aera.weather.forecast.domain.model.ForecastParams
import gi.aera.weather.forecast.domain.usecase.GetDailyForecastUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

class ForecastViewModel(
  private val permissionsController: PermissionsController,
  private val getLastLocationsUseCase: GetLastLocationUseCase,
  private val getSavedLocationsUseCase: GetSavedLocationUseCase,
  private val getDailyForecastUseCase: GetDailyForecastUseCase,
  private val forecastViewStateFactory: ForecastViewStateFactory,
) : ViewModel() {

  private val permission = Permission.LOCATION

  private val _state = MutableStateFlow<LceState<List<Forecast>>>(LceState.Loading)
  val viewState = _state
    .onStart { getForecastForLocation() }
    .stateIn(
      viewModelScope,
      SharingStarted.WhileSubscribed(C.RELOADING_TIMEOUT),
      LceState.Loading,
    )

  private val _effect = MutableSharedFlow<ForecastScreenViewEffect>()
  val effect = _effect.asSharedFlow()

  private fun getForecastForLocation() = viewModelScope.launch {
    val location = getSavedLocationsUseCase()
      .catch { e ->
        e.printStackTrace() // todo log error to see how often data store exception is thrown
        if (permissionsController.isPermissionGranted(permission)) {
          emit(getLastLocationsUseCase())
        } else {
          provideLocationPermission()
          throw CancellationException("Location permission not granted")
        }
        // todo catch data store exception
      }
      .first()

    getForecast(location)
  }

  private suspend fun getForecast(location: SearchLocation) {
    _state.update { LceState.Loading }

    val state = when (
      val response = getDailyForecastUseCase(
        ForecastParams(
          location = "${location.latitude}, ${location.longitude}",
        ),
      )
    ) {
      is ApiResponse.Error ->
        LceState.Error(Exception(response.errorMessage))

      is ApiResponse.Success<ForecastResponseDaily> ->
        LceState.Content(forecastViewStateFactory.createState(response.data, location))
    }
    _state.update { state }
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
