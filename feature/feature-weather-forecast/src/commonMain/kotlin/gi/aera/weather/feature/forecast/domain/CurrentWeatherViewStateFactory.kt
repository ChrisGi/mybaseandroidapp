package gi.aera.weather.feature.forecast.domain

import gi.aera.location.domain.model.SearchLocation
import gi.aera.ui.text.UiString
import gi.aera.weather.Res
import gi.aera.weather.domain.model.WeatherCode
import gi.aera.weather.forecast.domain.model.RealtimeWeatherResponse
import gi.aera.weather.forecast.domain.model.RealtimeWeatherValues
import gi.aera.weather.humidity
import gi.aera.weather.location_current
import gi.aera.weather.pressure
import gi.aera.weather.uv_index
import gi.aera.weather.visibility
import gi.aera.weather.water_drop
import gi.aera.weather.weather_condition_humidity
import gi.aera.weather.weather_condition_precipitation
import gi.aera.weather.weather_condition_pressure
import gi.aera.weather.weather_condition_uv
import gi.aera.weather.weather_condition_visibility
import gi.aera.weather.weather_condition_wind
import gi.aera.weather.weather_temperature_apparent
import gi.aera.weather.wind
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.DayOfWeekNames
import kotlinx.datetime.toLocalDateTime
import kotlin.math.roundToInt

class CurrentWeatherViewStateFactory {

  fun createState(
    response: RealtimeWeatherResponse,
    location: SearchLocation,
  ) = response.data.values.let { weatherValues ->
    CurrentConditions(
      weatherConditions = WeatherConditions(
        temperature = weatherValues.temperature.roundToInt().toString(),
        temperatureApparent = formatTemperatureApparent(weatherValues.temperatureApparent),
        conditionTitle = UiString.Resource(getWeatherCondition(weatherValues.weatherCode)),
        conditionIcon = getWeatherConditionIcon(weatherValues.weatherCode),
        weekday = formatWeekday(response.data.time),
        location = formatLocation(location),
        unitSystem = response.unitSystem,
      ),
      conditionValues = createOtherConditions(weatherValues),
    )
  }

  private fun createOtherConditions(weatherValues: RealtimeWeatherValues) =
    listOf(
      ConditionValue(
        Res.drawable.wind,
        UiString.Text(weatherValues.windSpeed.roundToInt().toString()),
        UiString.Resource(Res.string.weather_condition_wind),
      ),
      ConditionValue(
        Res.drawable.water_drop,
        UiString.Text(weatherValues.rainIntensity.toString()),
        UiString.Resource(Res.string.weather_condition_precipitation),
      ),
      ConditionValue(
        Res.drawable.pressure,
        UiString.Text(weatherValues.pressureSurfaceLevel?.roundToInt().toString()),
        UiString.Resource(Res.string.weather_condition_pressure),
      ),
      ConditionValue(
        Res.drawable.humidity,
        UiString.Text(weatherValues.humidity?.roundToInt().toString()),
        UiString.Resource(Res.string.weather_condition_humidity),
      ),
      ConditionValue(
        Res.drawable.visibility,
        UiString.Text(weatherValues.visibility.toString()),
        UiString.Resource(Res.string.weather_condition_visibility),
      ),
      ConditionValue(
        Res.drawable.uv_index,
        UiString.Text(weatherValues.uvHealthConcern?.roundToInt().toString()),
        UiString.Resource(Res.string.weather_condition_uv),
      ),
    )

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

  private fun formatTemperatureApparent(temperatureApparent: Double) =
    UiString.Resource(Res.string.weather_temperature_apparent, temperatureApparent.roundToInt())
}
