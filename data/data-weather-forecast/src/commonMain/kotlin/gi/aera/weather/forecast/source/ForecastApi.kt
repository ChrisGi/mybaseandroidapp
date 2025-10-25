package gi.aera.weather.forecast.source

import ForecastDailyResponse
import gi.aera.common.model.map
import gi.aera.network.cache.applyCacheControl
import gi.aera.network.domain.apiRequest
import gi.aera.weather.forecast.domain.model.ForecastHourlyResponse
import gi.aera.weather.forecast.domain.model.ForecastParams
import gi.aera.weather.forecast.domain.model.RealtimeWeatherResponse
import io.ktor.client.HttpClient
import io.ktor.client.plugins.resources.get

internal class ForecastApi(private val httpClient: HttpClient) {

  suspend fun realtimeWeather(params: ForecastParams) = apiRequest<RealtimeWeatherResponse> {
    httpClient.get(
      RealtimeWeatherResource.Realtime(
        location = params.location,
        units = params.units.name.lowercase(),
      ),
    ) {
      applyCacheControl(params.forceFreshData)
    }
  }.map { it.copy(unitSystem = params.units) }

  suspend fun forecastDaily(params: ForecastParams) = apiRequest<ForecastDailyResponse> {
    httpClient.get(
      ForecastResource.Forecast(
        location = params.location,
        timesteps = "1d",
        units = params.units.name.lowercase(),
      ),
    ) {
      applyCacheControl(params.forceFreshData)
    }
  }.map { it.copy(unitSystem = params.units) }

  suspend fun forecastHourly(params: ForecastParams) = apiRequest<ForecastHourlyResponse> {
    httpClient.get(
      ForecastResource.Forecast(
        location = params.location,
        timesteps = "1h",
        units = params.units.name.lowercase(),
      ),
    ) {
      applyCacheControl(params.forceFreshData)
    }
  }.map { it.copy(unitSystem = params.units) }
}
