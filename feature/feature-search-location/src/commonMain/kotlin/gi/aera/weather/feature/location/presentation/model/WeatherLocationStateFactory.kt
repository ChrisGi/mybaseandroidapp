package gi.aera.weather.feature.location.presentation.model

import gi.aera.location.domain.model.LocationSource
import gi.aera.location.domain.model.SearchLocation
import gi.aera.weather.presentation.formatter.WeatherConditionsFormatter
import gi.aera.weather.presentation.formatter.formatLocation
import gi.aera.weather.presentation.model.toTemperatureUnit
import gi.aera.weather.forecast.domain.model.RealtimeWeatherResponse
import kotlin.math.roundToInt

class WeatherLocationStateFactory {

  fun createState(
    response: RealtimeWeatherResponse,
    location: SearchLocation,
  ) = response.data.values.let { weatherValues ->
    WeatherLocationState.WeatherLocation(
      canBeDeleted = location.source == LocationSource.SEARCH,
      location = location.formatLocation(),
      temperature = weatherValues.temperature.roundToInt().toString(),
      temperatureApparent = WeatherConditionsFormatter.formatTemperatureApparent(weatherValues.temperatureApparent),
      temperatureUnit = response.unitSystem.toTemperatureUnit(),
      condition = WeatherConditionsFormatter.getWeatherConditionTitle(weatherValues.weatherCode),
      searchLocation = location,
    )
  }
}
