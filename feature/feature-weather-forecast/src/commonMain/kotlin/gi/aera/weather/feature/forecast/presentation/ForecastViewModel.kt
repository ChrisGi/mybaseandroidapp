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
import gi.aera.ui.updateLoading
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
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch

class ForecastViewModel(
  private val permissionsController: PermissionsController,
  private val getDefaultLocationUseCase: GetDefaultLocationUseCase,
  private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
  private val getDailyForecastUseCase: GetDailyForecastUseCase,
  private val currentWeatherViewStateFactory: CurrentWeatherViewStateFactory,
  private val weeklyForecastViewStateFactory: WeeklyForecastViewStateFactory,
  private val navigationManager: NavigationManager,
) : ViewModel(), EventHandler<ForecastScreenViewEvent> {

  private val permission = Permission.LOCATION

  private val refreshDataTrigger = MutableSharedFlow<Boolean>(replay = 1)
  private val refreshLocationTrigger = MutableSharedFlow<Unit>(replay = 1)

  private val location = refreshLocationTrigger
    .onStart { emit(Unit) }
    .flatMapLatest { getDefaultLocation() }
    .shareIn(
      viewModelScope,
      SharingStarted.WhileSubscribed(C.RELOADING_TIMEOUT),
      replay = 1,
    )

  val currentWeatherViewState = refreshDataTrigger
    .onStart { emit(false) }
    .flatMapLatest { forceRefresh -> getCurrentWeather(forceRefresh) }
    .stateIn(
      viewModelScope,
      SharingStarted.WhileSubscribed(C.RELOADING_TIMEOUT),
      LceState.Loading,
    )

  val forecastViewState = refreshDataTrigger
    .onStart { emit(false) }
    .flatMapLatest { forceRefresh -> getForecastWeather(forceRefresh) }
    .stateIn(
      viewModelScope,
      SharingStarted.WhileSubscribed(C.RELOADING_TIMEOUT),
      LceState.Loading,
    )

  val isRefreshing = currentWeatherViewState
    .map { it is LceState.Refreshing }

  override fun obtainEvent(event: ForecastScreenViewEvent) {
    when (event) {
      is ForecastScreenViewEvent.Retry -> refreshAllWeatherData()
      is ForecastScreenViewEvent.NavigateToSearchLocation -> navigateToSearchLocation(event.popUpInclusive)
      is ForecastScreenViewEvent.NavigateToNetworkSettings -> navigationManager.navigateTo(
        NavigationArgs(Route.SystemSettings(SettingType.NETWORK)),
      )
    }
  }

  private fun getDefaultLocation() = getDefaultLocationUseCase()
    .transform { locationResult ->
      when (locationResult) {
        LocationResult.NotFound -> navigateToSearchLocation(true)
        LocationResult.PermissionRequired -> provideLocationPermission()
        is LocationResult.Success -> emit(locationResult.location)
      }
    }

  private fun getCurrentWeather(forceRefresh: Boolean) = location
    .flatMapLatest { loadCurrentWeatherForLocation(it, forceRefresh) }
    .catchLceError()

  private fun loadCurrentWeatherForLocation(location: SearchLocation, forceRefresh: Boolean): Flow<LceState<CurrentConditions>> = flow {
    currentWeatherViewState.value.updateLoading { state -> emit(state) }

    val currentWeatherState = when (
      val response = getCurrentWeatherUseCase(
        location = "${location.latitude}, ${location.longitude}",
        forceRefresh = forceRefresh,
      )
    ) {
      is ApiResponse.Error -> LceState.Error(AppError.from(response))

      is ApiResponse.Success<RealtimeWeatherResponse> -> LceState.Content(
        content = currentWeatherViewStateFactory.createState(response.data, location),
      )
    }

    emit(currentWeatherState)
  }

  private fun getForecastWeather(forceRefresh: Boolean) = location
    .flatMapLatest { getForecastForLocation(it, forceRefresh) }
    .catchLceError()

  private fun getForecastForLocation(location: SearchLocation, forceRefresh: Boolean) = flow {
    val forecastState = when (
      val response = getDailyForecastUseCase(
        location = "${location.latitude}, ${location.longitude}",
        forceRefresh = forceRefresh,
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

        refreshLocationTrigger.emit(Unit)
      } catch (_: DeniedAlwaysException) {
        navigateToSearchLocation(true)
      } catch (_: DeniedException) {
        navigateToSearchLocation(true)
      } catch (_: RequestCanceledException) {
        navigateToSearchLocation(true)
      }
    }
  }

  private fun refreshAllWeatherData() {
    viewModelScope.launch {
      refreshDataTrigger.emit(true)
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
