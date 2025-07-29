package gi.aera.weather.feature.forecast.domain

import ForecastResponseDaily
import gi.aera.location.domain.model.SearchLocation
import gi.aera.ui.text.UiString
import gi.aera.weather.Res
import gi.aera.weather.domain.model.WeatherCode
import gi.aera.weather.location_current
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.DayOfWeekNames
import kotlinx.datetime.toLocalDateTime

class ForecastViewStateFactory {

  fun createState(
    response: ForecastResponseDaily,
    location: SearchLocation,
  ) = response.timelines.daily.map { daily ->
    val weatherValues = daily.values
    Forecast(
      currentTemperature = weatherValues.temperatureMax?.toInt().toString(),
      condition = UiString.Resource(getWeatherCondition(weatherValues.weatherCodeMax)),
      conditionIcon = getWeatherConditionIcon(weatherValues.weatherCodeMax),
      weekday = formatWeekday(daily.time),
      location = formatLocation(location),
    )
  }

  private fun formatWeekday(date: String) =
    Instant.parse(date).toLocalDateTime(TimeZone.currentSystemDefault()).date.format(
      LocalDate.Format {
        dayOfWeek(DayOfWeekNames.ENGLISH_ABBREVIATED)
      },
    ).lowercase()

  private fun formatLocation(location: SearchLocation) = when {
    location.city != null -> UiString.Text(location.city!!)
    location.formatted != null -> UiString.Text(location.formatted!!)
    else -> UiString.Resource(Res.string.location_current)
  }

  private fun getWeatherCondition(code: Int?) =
    code?.let { WeatherCode.fromCode(it).conditionStringRes } ?: WeatherCode.UNKNOWN.conditionStringRes

  private fun getWeatherConditionIcon(code: Int?): String {
    val iconResName = code?.let { WeatherCode.fromCode(it).conditionIcon } ?: WeatherCode.UNKNOWN.conditionIcon
    return "files/$iconResName.svg"
  }
}
