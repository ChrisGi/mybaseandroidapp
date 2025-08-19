package gi.aera.weather.feature.location.domain

import ForecastDailyResponse
import gi.aera.location.domain.model.LocationSource
import gi.aera.location.domain.model.SearchLocation
import gi.aera.ui.text.UiString
import gi.aera.weather.Res
import gi.aera.weather.domain.model.WeatherCode
import gi.aera.weather.location_current

class WeatherLocationFactory {

  fun createState(
    response: ForecastDailyResponse,
    location: SearchLocation,
  ) = response.timelines.daily
    .first()
    .let { daily ->
      val weatherValues = daily.values
      WeatherLocationState.WeatherLocation(
        canBeDeleted = location.source == LocationSource.SEARCH,
        location = formatLocation(location),
        temperatureAvg = weatherValues.temperatureMax?.toInt().toString(),
        temperatureMin = weatherValues.temperatureMin?.toInt().toString(),
        temperatureMax = weatherValues.temperatureMax?.toInt().toString(),
        condition = UiString.Resource(getWeatherCondition(weatherValues.weatherCodeMax)),
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
