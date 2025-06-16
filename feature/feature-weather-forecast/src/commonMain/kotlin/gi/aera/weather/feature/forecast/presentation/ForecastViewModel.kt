package gi.aera.weather.feature.forecast.presentation

import ForecastResponseDaily
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gi.aera.location.domain.usecase.GetCurrentLocationUseCase
import gi.aera.network.di.domain.ApiResponse
import gi.aera.ui.LceState
import gi.aera.weather.feature.forecast.domain.ForecastViewState
import gi.aera.weather.feature.forecast.domain.ForecastViewStateFactory
import gi.aera.weather.forecast.domain.model.ForecastParams
import gi.aera.weather.forecast.domain.usecase.GetDailyForecastUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ForecastViewModel(
  private val getLocationsUseCase: GetCurrentLocationUseCase,
  private val getDailyForecastUseCase: GetDailyForecastUseCase,
  private val forecastViewStateFactory: ForecastViewStateFactory,
) : ViewModel() {

  private val _state = MutableStateFlow<LceState<List<ForecastViewState>>>(LceState.Loading)

  val viewState = _state
    .onStart { loadCurrentConditionsForecast() }
    .stateIn(
      viewModelScope,
      SharingStarted.WhileSubscribed(RELOADING_TIMEOUT),
      LceState.Loading,
    )

  private fun loadCurrentConditionsForecast() {
    viewModelScope.launch {
      _state.update { LceState.Loading }

      val location = getLocationsUseCase()

      when (val forecastResponse = getDailyForecastUseCase(ForecastParams(location = location.toString()))) {
        is ApiResponse.Error -> _state.update {
          LceState.Error(Exception(forecastResponse.errorMessage))
        }

        is ApiResponse.Success<ForecastResponseDaily> -> _state.update {
          LceState.Success(forecastViewStateFactory.createState(forecastResponse.data, location))
        }
      }
    }
  }

  companion object {
    private const val RELOADING_TIMEOUT = 5000L
  }
}
