package gi.aera.weather.feature.forecast.domain

import ForecastResponseDaily
import gi.aera.location.domain.model.LocationSource
import gi.aera.ui.text.UiString
import gi.aera.weather.Res
import gi.aera.weather.location_current
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class ForecastViewStateFactory {

  fun createState(
    response: ForecastResponseDaily,
    locationSource: LocationSource,
  ) = response.timelines.daily.map { daily ->
    val weatherValues = daily.values
    ForecastViewState(
      currentTemperature = weatherValues.temperatureAvg?.toInt().toString(),
      condition = UiString.Resource(getWeatherCondition(weatherValues.weatherCodeMin)),
      conditionIcon = getWeatherConditionIcon(weatherValues.weatherCodeMin),
      localDate = Instant.parse(daily.time).toLocalDateTime(TimeZone.currentSystemDefault()).date,
      location = formatLocation(locationSource),
    )
  }

  private fun formatLocation(locationSource: LocationSource) = when (locationSource) {
    is LocationSource.City -> UiString.Text(locationSource.name)
    is LocationSource.GpsCoordinates -> UiString.Resource(Res.string.location_current)
  }

  private fun getWeatherCondition(code: Int?) =
    code?.let { WeatherCode.fromCode(it).conditionStringRes } ?: WeatherCode.UNKNOWN.conditionStringRes

  private fun getWeatherConditionIcon(code: Int?): String {
    val iconResName = code?.let { WeatherCode.fromCode(it).conditionIcon } ?: WeatherCode.UNKNOWN.conditionIcon
    return "files/$iconResName.svg"
  }
}
