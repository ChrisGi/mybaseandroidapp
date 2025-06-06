package gi.aera.weather.feature.forecast.domain

import ForecastResponseDaily
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class ForecastViewStateFactory {

  fun createState(response: ForecastResponseDaily) = response.timelines.daily.map { daily ->
    val weatherValues = daily.values
    ForecastViewState(
      currentTemperature = weatherValues.temperatureAvg?.toInt().toString(),
      condition = getWeatherCondition(weatherValues.weatherCodeMin),
      conditionIcon = getWeatherConditionIcon(weatherValues.weatherCodeMin),
      localDate = Instant.parse(daily.time).toLocalDateTime(TimeZone.currentSystemDefault()).date
    )
  }

  private fun getWeatherCondition(code: Int?) =
    code?.let { WeatherCode.fromCode(it).conditionStringRes } ?: WeatherCode.UNKNOWN.conditionStringRes

  private fun getWeatherConditionIcon(code: Int?): String {
    val iconResName = code?.let { WeatherCode.fromCode(it).conditionIcon } ?: WeatherCode.UNKNOWN.conditionIcon
    return "files/$iconResName.svg"
  }
}
