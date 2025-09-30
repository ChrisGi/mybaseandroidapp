package gi.aera.weather.feature.forecast.domain

import ForecastDailyResponse
import gi.aera.location.domain.model.SearchLocation
import gi.aera.ui.text.UiString
import gi.aera.weather.Res
import gi.aera.weather.domain.model.WeatherCode
import gi.aera.weather.location_current
import gi.aera.weather.weather_temperature_apparent
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.DayOfWeekNames
import kotlinx.datetime.toLocalDateTime

class ForecastViewStateFactory {

  fun createState(
    response: ForecastDailyResponse,
    location: SearchLocation,
  ) = response.timelines.daily.map { daily ->
    val weatherValues = daily.values
    WeatherConditions(
      temperature = weatherValues.temperatureMax?.toInt().toString(),
      temperatureApparent = formatTemperatureApparent(weatherValues.temperatureApparentAvg),
      conditionTitle = UiString.Resource(getWeatherCondition(weatherValues.weatherCodeMax)),
      conditionIcon = getWeatherConditionIcon(weatherValues.weatherCodeMax),
      weekday = formatWeekday(daily.time),
      location = formatLocation(location),
      unitSystem = response.unitSystem,
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

  private fun getWeatherConditionIcon(code: Int?) =
    code?.let { WeatherCode.fromCode(it).conditionIcon } ?: WeatherCode.UNKNOWN.conditionIcon

  private fun formatTemperatureApparent(temperatureApparent: Double?) =
    temperatureApparent?.let { UiString.Resource(Res.string.weather_temperature_apparent, it) } ?: UiString.Empty
}
