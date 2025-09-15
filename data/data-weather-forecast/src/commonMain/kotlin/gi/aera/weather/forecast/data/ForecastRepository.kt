package gi.aera.weather.forecast.data

import ForecastDailyResponse
import gi.aera.domain.model.ApiResponse
import gi.aera.weather.forecast.domain.model.ForecastHourlyResponse
import gi.aera.weather.forecast.domain.model.ForecastParams
import gi.aera.weather.forecast.domain.model.RealtimeWeatherResponse

interface ForecastRepository {
  suspend fun realtimeWeather(params: ForecastParams): ApiResponse<RealtimeWeatherResponse>
  suspend fun forecastDaily(params: ForecastParams): ApiResponse<ForecastDailyResponse>
  suspend fun forecastHourly(params: ForecastParams): ApiResponse<ForecastHourlyResponse>
}
