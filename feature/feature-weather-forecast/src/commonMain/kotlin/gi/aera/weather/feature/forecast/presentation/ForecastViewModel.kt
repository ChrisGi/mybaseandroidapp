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
import gi.aera.domain.model.ApiResponse
import gi.aera.domain.model.AppError
import gi.aera.location.domain.model.LocationResult
import gi.aera.location.domain.model.SearchLocation
import gi.aera.location.domain.usecase.GetDefaultLocationUseCase
import gi.aera.ui.C
import gi.aera.ui.EventHandler
import gi.aera.ui.LceState
import gi.aera.ui.navigation.NavigationArgs
import gi.aera.ui.navigation.Route
import gi.aera.ui.navigation.SettingType
import gi.aera.ui.navigation.domain.model.NavigationManager
import gi.aera.weather.feature.forecast.domain.CurrentConditions
import gi.aera.weather.feature.forecast.domain.CurrentWeatherViewStateFactory
import gi.aera.weather.feature.forecast.domain.ForecastScreenViewEvent
import gi.aera.weather.feature.forecast.domain.WeeklyForecastViewStateFactory
import gi.aera.weather.forecast.domain.model.RealtimeWeatherResponse
import gi.aera.weather.forecast.domain.usecase.GetCurrentWeatherUseCase
import gi.aera.weather.forecast.domain.usecase.GetDailyForecastUseCase
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch

class ForecastViewModel(
  private val permissionsController: PermissionsController,
  getDefaultLocationUseCase: GetDefaultLocationUseCase,
  private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
  private val getDailyForecastUseCase: GetDailyForecastUseCase,
  private val currentWeatherViewStateFactory: CurrentWeatherViewStateFactory,
  private val weeklyForecastViewStateFactory: WeeklyForecastViewStateFactory,
  private val navigationManager: NavigationManager,
) : ViewModel(), EventHandler<ForecastScreenViewEvent> {

  private val permission = Permission.LOCATION

  private val reloadDataTrigger = MutableSharedFlow<Unit>(replay = 1)
  private val reloadLocationTrigger = MutableSharedFlow<Unit>(replay = 1)

  private val location = reloadLocationTrigger
    .onStart { emit(Unit) }
    .flatMapLatest {
      getDefaultLocationUseCase()
        .transform { locationResult ->
          when (locationResult) {
            LocationResult.NotFound -> navigateToSearchLocation(true)
            LocationResult.PermissionRequired -> provideLocationPermission()
            is LocationResult.Success -> emit(locationResult.location)
          }
        }
    }
    .shareIn(viewModelScope, SharingStarted.WhileSubscribed(C.RELOADING_TIMEOUT), replay = 1)

  val currentWeatherViewState = reloadDataTrigger
    .onStart { emit(Unit) }
    .flatMapLatest { getCurrentWeather() }
    .stateIn(
      viewModelScope,
      SharingStarted.WhileSubscribed(C.RELOADING_TIMEOUT),
      LceState.Loading,
    )

  val forecastViewState = reloadDataTrigger
    .onStart { emit(Unit) }
    .flatMapLatest { getForecastWeather() }
    .stateIn(
      viewModelScope,
      SharingStarted.WhileSubscribed(C.RELOADING_TIMEOUT),
      LceState.Loading,
    )

  override fun obtainEvent(event: ForecastScreenViewEvent) {
    when (event) {
      is ForecastScreenViewEvent.Retry -> refreshAllWeatherData()
      is ForecastScreenViewEvent.NavigateToSearchLocation -> navigateToSearchLocation(event.popUpInclusive)
      is ForecastScreenViewEvent.NavigateToNetworkSettings -> navigationManager.navigateTo(
        NavigationArgs(Route.SystemSettings(SettingType.NETWORK)),
      )
    }
  }

  private fun getCurrentWeather() = location
    .flatMapLatest { loadCurrentWeatherForLocation(it) }
    .catchLceError()

  private fun loadCurrentWeatherForLocation(location: SearchLocation): Flow<LceState<CurrentConditions>> = flow {
    emit(LceState.Loading)

    val currentWeatherState = when (
      val response = getCurrentWeatherUseCase(
        location = "${location.latitude}, ${location.longitude}",
      )
    ) {
      is ApiResponse.Error -> LceState.Error(AppError.from(response))

      is ApiResponse.Success<RealtimeWeatherResponse> -> LceState.Content(
        currentWeatherViewStateFactory.createState(response.data, location),
      )
    }

    emit(currentWeatherState)
  }

  private fun getForecastWeather() = location
    .flatMapLatest { getForecastForLocation(it) }
    .catchLceError()

  private fun getForecastForLocation(location: SearchLocation) = flow {
    emit(LceState.Loading)

    val forecastState = when (
      val response = getDailyForecastUseCase(
        location = "${location.latitude}, ${location.longitude}",
      )
    ) {
      is ApiResponse.Error -> LceState.Error(AppError.from(response))

      is ApiResponse.Success<ForecastDailyResponse> ->
        LceState.Content(weeklyForecastViewStateFactory.createState(response.data, location))
    }

    emit(forecastState)
  }

  private fun provideLocationPermission() {
    viewModelScope.launch {
      try {
        permissionsController.providePermission(permission)

        reloadLocationTrigger.emit(Unit)
      } catch (e: DeniedAlwaysException) {
        e.printStackTrace()
        navigateToSearchLocation(true)
      } catch (e: DeniedException) {
        e.printStackTrace()
        navigateToSearchLocation(true)
      } catch (e: RequestCanceledException) {
        e.printStackTrace()
        navigateToSearchLocation(true)
      }
    }
  }

  private fun refreshAllWeatherData() {
    viewModelScope.launch {
      reloadDataTrigger.emit(Unit)
    }
  }

  private fun navigateToSearchLocation(popUpInclusive: Boolean) {
    val popUpTo = if (popUpInclusive) Pair(Route.ForecastNavScreen, true) else null
    val navArgs = NavigationArgs(
      route = Route.SearchLocationNavScreen,
      popUpTo = popUpTo,
    )
    navigationManager.navigateTo(navArgs)
  }

  private fun <T> Flow<LceState<T>>.catchLceError(): Flow<LceState<T>> = catch { e ->
    if (e is CancellationException) throw e

    emit(LceState.Error(AppError.from(e)))
  }
}
