package gi.aera.weather.feature.location.domain

import ForecastResponseDaily
import gi.aera.location.domain.model.SearchLocation
import gi.aera.ui.text.UiString
import gi.aera.weather.Res
import gi.aera.weather.domain.model.WeatherCode
import gi.aera.weather.location_current

class WeatherLocationFactory {

  fun createState(
    response: ForecastResponseDaily,
    location: SearchLocation,
  ) = response.timelines.daily
    .first()
    .let { daily ->
      val weatherValues = daily.values
      WeatherLocation(
        location = formatLocation(location),
        temperatureAvg = weatherValues.temperatureMax?.toInt().toString(),
        temperatureMin = weatherValues.temperatureMin?.toInt().toString(),
        temperatureMax = weatherValues.temperatureMax?.toInt().toString(),
        condition = UiString.Resource(getWeatherCondition(weatherValues.weatherCodeMax)),
        searchLocation = location,
      )
    }

  private fun formatLocation(location: SearchLocation) = when {
    location.city != null -> UiString.Text(location.city!!)
    location.formatted != null -> UiString.Text(location.formatted!!)
    else -> UiString.Resource(Res.string.location_current)
  }

  private fun getWeatherCondition(code: Int?) =
    code?.let { WeatherCode.fromCode(it).conditionStringRes } ?: WeatherCode.UNKNOWN.conditionStringRes
}
