package gi.aera.weather.forecast.data

import gi.aera.weather.forecast.domain.model.ForecastParams
import gi.aera.weather.forecast.domain.repository.ForecastRepository
import gi.aera.weather.forecast.source.ForecastApi

internal class ForecastRepositoryImpl internal constructor(
  private val forecastApi: ForecastApi,
) : ForecastRepository {

  override suspend fun realtimeWeather(params: ForecastParams) = forecastApi.realtimeWeather(params)

  override suspend fun forecastDaily(params: ForecastParams) = forecastApi.forecastDaily(params)

  override suspend fun forecastHourly(params: ForecastParams) = forecastApi.forecastHourly(params)
}
