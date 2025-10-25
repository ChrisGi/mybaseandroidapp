package gi.aera.weather.feature.forecast.presentation.model

import ForecastDailyResponse
import gi.aera.location.domain.model.SearchLocation
import gi.aera.weather.presentation.formatter.WeatherConditionsFormatter
import gi.aera.weather.presentation.formatter.WeatherDateTimeFormatter
import gi.aera.weather.presentation.formatter.formatLocation
import gi.aera.weather.presentation.model.toTemperatureUnit
import kotlin.math.roundToInt

class WeeklyForecastViewStateFactory {

  fun createState(
    response: ForecastDailyResponse,
    location: SearchLocation,
  ) = response.timelines.daily.map { daily ->
    val weatherValues = daily.values
    WeatherConditions(
      temperature = weatherValues.temperatureMax?.roundToInt().toString(),
      temperatureApparent = WeatherConditionsFormatter.formatTemperatureApparent(weatherValues.temperatureApparentAvg),
      temperatureUnit = response.unitSystem.toTemperatureUnit(),
      conditionTitle = WeatherConditionsFormatter.getWeatherConditionTitle(weatherValues.weatherCodeMax),
      conditionIcon = WeatherConditionsFormatter.getWeatherConditionIcon(weatherValues.weatherCodeMax),
      moment = WeatherDateTimeFormatter.formatWeekdayShort(daily.time),
      location = location.formatLocation(),
    )
  }
}
