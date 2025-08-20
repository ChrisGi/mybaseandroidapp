package gi.aera.weather.forecast.data

import gi.aera.weather.forecast.domain.model.ForecastParams

internal class ForecastRepositoryImpl internal constructor(
  private val forecastApi: ForecastApi,
) : ForecastRepository {

  override suspend fun realtimeWeather(params: ForecastParams) = forecastApi.realtimeWeather(params)

  override suspend fun forecastDaily(params: ForecastParams) = forecastApi.forecastDaily(params)

  override suspend fun forecastHourly(params: ForecastParams) = forecastApi.forecastHourly(params)
}
