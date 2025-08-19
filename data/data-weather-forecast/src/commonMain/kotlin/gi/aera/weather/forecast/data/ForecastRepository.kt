package gi.aera.weather.forecast.data

import gi.aera.weather.forecast.domain.model.ForecastParams

internal class ForecastRepository internal constructor(private val forecastApi: ForecastApi) {

  suspend fun realtimeWeather(params: ForecastParams) = forecastApi.realtimeWeather(params)

  suspend fun forecastDaily(params: ForecastParams) = forecastApi.forecastDaily(params)

  suspend fun forecastHourly(params: ForecastParams) = forecastApi.forecastHourly(params)
}
