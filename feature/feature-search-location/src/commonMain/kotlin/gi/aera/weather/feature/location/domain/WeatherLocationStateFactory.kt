package gi.aera.weather.feature.location.domain

import gi.aera.location.domain.model.LocationSource
import gi.aera.location.domain.model.SearchLocation
import gi.aera.ui.text.UiString
import gi.aera.weather.Res
import gi.aera.weather.domain.model.WeatherCode
import gi.aera.weather.forecast.domain.model.RealtimeWeatherResponse
import gi.aera.weather.location_current

class WeatherLocationStateFactory {

  fun createState(
    response: RealtimeWeatherResponse,
    location: SearchLocation,
  ) = response.data.values
    .let { weatherValues ->
      WeatherLocationState.WeatherLocation(
        canBeDeleted = location.source == LocationSource.SEARCH,
        location = formatLocation(location),
        temperature = weatherValues.temperature?.toInt().toString(),
        temperatureApparent = weatherValues.temperatureApparent?.toInt().toString(),
        condition = UiString.Resource(getWeatherCondition(weatherValues.weatherCode)),
        searchLocation = location,
        unitSystem = response.unitSystem,
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
