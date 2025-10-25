package gi.aera.weather.feature.forecast.presentation.model

import gi.aera.appsettings.domain.model.UnitSystem
import gi.aera.location.domain.model.SearchLocation
import gi.aera.ui.C.DEFAULT_UI_VALUE
import gi.aera.ui.text.UiString
import gi.aera.weather.Res
import gi.aera.weather.presentation.formatter.WeatherConditionsFormatter
import gi.aera.weather.presentation.formatter.WeatherDateTimeFormatter
import gi.aera.weather.presentation.formatter.formatLocation
import gi.aera.weather.presentation.model.toTemperatureUnit
import gi.aera.weather.presentation.model.unitSystemValues
import gi.aera.weather.presentation.model.uvHealthConcern
import gi.aera.weather.forecast.domain.model.RealtimeWeatherResponse
import gi.aera.weather.forecast.domain.model.RealtimeWeatherValues
import gi.aera.weather.humidity
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
import gi.aera.weather.wind
import kotlin.math.roundToInt

class CurrentWeatherViewStateFactory {

  fun createState(
    response: RealtimeWeatherResponse,
    location: SearchLocation,
  ) = response.data.values.let { weatherValues ->
    CurrentConditions(
      weatherConditions = WeatherConditions(
        temperature = weatherValues.temperature.roundToInt().toString(),
        temperatureApparent = WeatherConditionsFormatter.formatTemperatureApparent(weatherValues.temperatureApparent),
        temperatureUnit = response.unitSystem.toTemperatureUnit(),
        conditionTitle = WeatherConditionsFormatter.getWeatherConditionTitle(weatherValues.weatherCode),
        conditionIcon = WeatherConditionsFormatter.getWeatherConditionIcon(weatherValues.weatherCode),
        moment = WeatherDateTimeFormatter.formatFullDate(response.data.time),
        location = location.formatLocation(),
      ),
      conditionValues = createOtherConditions(weatherValues, response.unitSystem),
    )
  }

  private fun createOtherConditions(
    weatherValues: RealtimeWeatherValues,
    unitSystem: UnitSystem,
  ): List<ConditionValue> {
    val unitSystemValues = unitSystem.unitSystemValues()
    return listOf(
      ConditionValue(
        Res.drawable.wind,
        UiString.Text(
          weatherValues.windSpeed?.let { windSpeed ->
            "${windSpeed.roundToInt()}${unitSystemValues.windSpeed}"
          } ?: DEFAULT_UI_VALUE,
        ),
        UiString.Resource(Res.string.weather_condition_wind),
      ),
      ConditionValue(
        Res.drawable.water_drop,
        UiString.Text(
          weatherValues.rainIntensity?.let { rainIntensity ->
            "${rainIntensity.roundToInt()}${unitSystemValues.rainIntensity}"
          } ?: DEFAULT_UI_VALUE,
        ),
        UiString.Resource(Res.string.weather_condition_precipitation),
      ),
      ConditionValue(
        Res.drawable.pressure,
        UiString.Text(
          weatherValues.pressureSurfaceLevel?.let { pressLev ->
            "${pressLev.roundToInt()}${unitSystemValues.pressure}"
          } ?: DEFAULT_UI_VALUE,
        ),
        UiString.Resource(Res.string.weather_condition_pressure),
      ),
      ConditionValue(
        Res.drawable.humidity,
        UiString.Text(
          weatherValues.humidity?.let { hum ->
            "${hum.roundToInt()}${unitSystemValues.humidity}"
          } ?: DEFAULT_UI_VALUE,
        ),
        UiString.Resource(Res.string.weather_condition_humidity),
      ),
      ConditionValue(
        Res.drawable.visibility,
        UiString.Text(
          weatherValues.visibility?.let { visibility ->
            "$visibility${unitSystemValues.visibility}"
          } ?: DEFAULT_UI_VALUE,
        ),
        UiString.Resource(Res.string.weather_condition_visibility),
      ),
      ConditionValue(
        Res.drawable.uv_index,
        weatherValues.uvIndex?.let {
          UiString.Resource(uvHealthConcern(it.toInt()))
        } ?: UiString.Text(DEFAULT_UI_VALUE),
        UiString.Resource(Res.string.weather_condition_uv),
      ),
    )
  }
}
