package gi.aera.weather.forecast.data

import ForecastDailyResponse
import gi.aera.network.di.domain.apiRequest
import gi.aera.network.di.domain.map
import gi.aera.weather.forecast.domain.model.ForecastHourlyResponse
import gi.aera.weather.forecast.domain.model.ForecastParams
import gi.aera.weather.forecast.domain.model.ForecastResource
import io.ktor.client.HttpClient
import io.ktor.client.plugins.resources.get

internal class ForecastApi(private val httpClient: HttpClient) {

  suspend fun forecastDaily(params: ForecastParams) = httpClient.apiRequest<ForecastDailyResponse> {
    httpClient.get(
      ForecastResource.Forecast(
        location = params.location,
        timesteps = "1d",
        units = params.units.name.lowercase(),
      ),
    )
  }.map { it.copy(unitSystem = params.units) }

  suspend fun forecastHourly(params: ForecastParams) = httpClient.apiRequest<ForecastHourlyResponse> {
    httpClient.get(
      ForecastResource.Forecast(
        location = params.location,
        timesteps = "1h",
        units = params.units.name.lowercase(),
      ),
    )
  }.map { it.copy(unitSystem = params.units) }
}
