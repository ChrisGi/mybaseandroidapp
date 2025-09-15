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
import gi.aera.location.domain.model.PermissionException
import gi.aera.location.domain.model.SearchLocation
import gi.aera.location.domain.usecase.GetDefaultLocationUseCase
import gi.aera.ui.C
import gi.aera.ui.EventHandler
import gi.aera.ui.LceState
import gi.aera.ui.navigation.NavigationArgs
import gi.aera.ui.navigation.NavigationManager
import gi.aera.ui.navigation.Route
import gi.aera.weather.feature.forecast.domain.ForecastScreenViewEvent
import gi.aera.weather.feature.forecast.domain.ForecastViewStateFactory
import gi.aera.weather.feature.forecast.domain.RealtimeWeatherViewStateFactory
import gi.aera.weather.feature.forecast.domain.WeatherConditions
import gi.aera.weather.forecast.domain.model.RealtimeWeatherResponse
import gi.aera.weather.forecast.domain.usecase.GetCurrentWeatherUseCase
import gi.aera.weather.forecast.domain.usecase.GetDailyForecastUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

@Suppress("LongParameterList")
class ForecastViewModel(
  private val permissionsController: PermissionsController,
  private val getDefaultLocationUseCase: GetDefaultLocationUseCase,
  private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
  private val getDailyForecastUseCase: GetDailyForecastUseCase,
  private val realtimeWeatherViewStateFactory: RealtimeWeatherViewStateFactory,
  private val forecastViewStateFactory: ForecastViewStateFactory,
  private val navigationManager: NavigationManager,
) : ViewModel(), EventHandler<ForecastScreenViewEvent> {

  private val permission = Permission.LOCATION

  private val _currentWeatherViewState = MutableStateFlow<LceState<WeatherConditions>>(LceState.Loading)
  val currentWeatherViewState = _currentWeatherViewState
    .onStart { getCurrentWeatherForLocation() }
    .stateIn(
      viewModelScope,
      SharingStarted.WhileSubscribed(C.RELOADING_TIMEOUT),
      LceState.Loading,
    )

  private val _forecastViewState = MutableStateFlow<LceState<List<WeatherConditions>>>(LceState.Loading)
  val forecastViewState = _forecastViewState
    .stateIn(
      viewModelScope,
      SharingStarted.WhileSubscribed(C.RELOADING_TIMEOUT),
      LceState.Loading,
    )

  override fun obtainEvent(event: ForecastScreenViewEvent) {
    when (event) {
      is ForecastScreenViewEvent.Retry -> getCurrentWeatherForLocation()

      is ForecastScreenViewEvent.NavigateToSearchLocation -> navigateToSearchLocation(event.popUpInclusive)
    }
  }

  @OptIn(ExperimentalCoroutinesApi::class)
  private fun getCurrentWeatherForLocation() = viewModelScope.launch {
    defaultLocation()
      .onStart { _currentWeatherViewState.update { LceState.Loading } }
      .transform { location ->
        val lceState = when (
          val response = getCurrentWeatherUseCase(location = "${location.latitude}, ${location.longitude}")
        ) {
          is ApiResponse.Error -> {
            LceState.Error(AppError.from(response))
          }

          is ApiResponse.Success<RealtimeWeatherResponse> ->
            LceState.Content(realtimeWeatherViewStateFactory.createState(response.data, location))
        }
        _currentWeatherViewState.update { lceState }

        when (lceState) {
          is LceState.Error -> throw CancellationException("Error loading current weather")

          else -> emit(location)
        }
      }
      .flatMapLatest { location -> getForecastForLocation(location) }
      .collect { _ -> }
  }

  private fun getForecastForLocation(location: SearchLocation) = flow {
    val state = when (
      val response = getDailyForecastUseCase(location = "${location.latitude}, ${location.longitude}")
    ) {
      is ApiResponse.Error ->
        LceState.Error(AppError.from(response))

      is ApiResponse.Success<ForecastDailyResponse> ->
        LceState.Content(forecastViewStateFactory.createState(response.data, location))
    }
    emit(state)
  }.onEach { lceState -> _forecastViewState.update { lceState } }

  private fun defaultLocation() = getDefaultLocationUseCase()
    .catch { e ->
      when {
        e is PermissionException -> {
          provideLocationPermission()
          throw CancellationException("Location permission not granted")
        }

        else -> {
          navigateToSearchLocation(true)
          throw CancellationException("Location not found")
        }
      }
    }
    .shareIn(
      viewModelScope,
      SharingStarted.WhileSubscribed(),
    )

  private fun provideLocationPermission() {
    viewModelScope.launch {
      try {
        permissionsController.providePermission(permission)

        getCurrentWeatherForLocation()
      } catch (e: DeniedAlwaysException) {
        e.printStackTrace()
        navigateToSearchLocation(true)
      } catch (e: DeniedException) {
        e.printStackTrace()
        navigateToSearchLocation(true)
      } catch (e: RequestCanceledException) {
        e.printStackTrace()
      }
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
}
