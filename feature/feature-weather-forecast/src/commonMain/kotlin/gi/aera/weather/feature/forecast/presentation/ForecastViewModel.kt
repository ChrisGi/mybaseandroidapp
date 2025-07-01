package gi.aera.weather.feature.forecast.presentation

import ForecastResponseDaily
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gi.aera.location.domain.model.LocationNotFoundException
import gi.aera.location.domain.usecase.GetLastLocationUseCase
import gi.aera.location.domain.usecase.GetSavedLocationUseCase
import gi.aera.network.di.domain.ApiResponse
import gi.aera.ui.C
import gi.aera.ui.LceState
import gi.aera.weather.feature.forecast.domain.Forecast
import gi.aera.weather.feature.forecast.domain.ForecastViewStateFactory
import gi.aera.weather.forecast.domain.model.ForecastParams
import gi.aera.weather.forecast.domain.usecase.GetDailyForecastUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class ForecastViewModel(
  private val getLastLocationsUseCase: GetLastLocationUseCase,
  private val getSavedLocationsUseCase: GetSavedLocationUseCase,
  private val getDailyForecastUseCase: GetDailyForecastUseCase,
  private val forecastViewStateFactory: ForecastViewStateFactory,
) : ViewModel() {

  private val _state = MutableStateFlow<LceState<List<Forecast>>>(LceState.Loading)
  val viewState = getSavedLocationsUseCase()
    .catch { e ->
      if (e is LocationNotFoundException) {
        emit(getLastLocationsUseCase())
      }
    }
    .combine(_state) { location, _ ->
      return@combine when (
        val response = getDailyForecastUseCase(ForecastParams(location = "${location.latitude}, ${location.longitude}"))
      ) {
        is ApiResponse.Error ->
          LceState.Error(Exception(response.errorMessage))

        is ApiResponse.Success<ForecastResponseDaily> ->
          LceState.Content(forecastViewStateFactory.createState(response.data, location))
      }
    }
    .stateIn(
      viewModelScope,
      SharingStarted.WhileSubscribed(C.RELOADING_TIMEOUT),
      LceState.Loading,
    )
}
