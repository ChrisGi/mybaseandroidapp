package gi.aera.weather.feature.location.presentation

import ForecastResponseDaily
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gi.aera.location.domain.model.SearchLocation
import gi.aera.network.di.domain.ApiResponse
import gi.aera.ui.LceState
import gi.aera.weather.feature.location.domain.WeatherLocation
import gi.aera.weather.feature.location.domain.WeatherLocationFactory
import gi.aera.weather.forecast.domain.model.ForecastParams
import gi.aera.weather.forecast.domain.usecase.GetDailyForecastUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WeatherLocationViewModel(
  private val getWeatherForecastUseCase: GetDailyForecastUseCase,
  private val weatherLocationFactory: WeatherLocationFactory,
) : ViewModel() {

  private val _weatherLocationState = MutableStateFlow<LceState<WeatherLocation>>(LceState.Loading)
  val weatherLocationState = _weatherLocationState.asStateFlow()

  fun getWeatherLocation(location: SearchLocation) = viewModelScope.launch {
    _weatherLocationState.update { LceState.Loading }

    val params = ForecastParams(location = "${location.latitude}, ${location.longitude}")
    when (val response = getWeatherForecastUseCase(params)) {
      is ApiResponse.Error ->
        _weatherLocationState.update { LceState.Error(Exception(response.errorMessage)) }

      is ApiResponse.Success<ForecastResponseDaily> ->
        _weatherLocationState.update { LceState.Content(weatherLocationFactory.createState(response.data, location)) }
    }
  }
}
