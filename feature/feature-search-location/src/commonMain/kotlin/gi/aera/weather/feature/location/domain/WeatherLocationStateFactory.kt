package gi.aera.weather.feature.location.domain

import gi.aera.location.domain.model.LocationSource
import gi.aera.location.domain.model.SearchLocation
import gi.aera.ui.text.UiString
import gi.aera.weather.Res
import gi.aera.weather.domain.model.WeatherCode
import gi.aera.weather.domain.model.toTemperatureUnit
import gi.aera.weather.forecast.domain.model.RealtimeWeatherResponse
import gi.aera.weather.location_current
import kotlin.math.roundToInt

class WeatherLocationStateFactory {

  fun createState(
    response: RealtimeWeatherResponse,
    location: SearchLocation,
  ) = response.data.values
    .let { weatherValues ->
      WeatherLocationState.WeatherLocation(
        canBeDeleted = location.source == LocationSource.SEARCH,
        location = formatLocation(location),
        temperature = weatherValues.temperature.roundToInt().toString(),
        temperatureApparent = weatherValues.temperatureApparent.roundToInt().toString(),
        temperatureUnit = response.unitSystem.toTemperatureUnit(),
        condition = UiString.Resource(getWeatherCondition(weatherValues.weatherCode)),
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
