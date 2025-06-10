package gi.aera.weather.forecast.data

import ForecastResponseDaily
import ForecastResponseHourly
import gi.aera.network.di.domain.apiRequest
import gi.aera.weather.forecast.domain.model.ForecastParams
import gi.aera.weather.forecast.domain.model.ForecastResource
import io.ktor.client.HttpClient
import io.ktor.client.plugins.resources.get

internal class ForecastApi(private val httpClient: HttpClient) {

  suspend fun forecastDaily(params: ForecastParams) = httpClient.apiRequest<ForecastResponseDaily> {
    httpClient.get(ForecastResource.Forecast(location = params.location, timesteps = "1d"))
  }

  suspend fun forecastHourly(params: ForecastParams) = httpClient.apiRequest<ForecastResponseHourly> {
    httpClient.get(ForecastResource.Forecast(location = params.location, timesteps = "1h"))
  }
}
