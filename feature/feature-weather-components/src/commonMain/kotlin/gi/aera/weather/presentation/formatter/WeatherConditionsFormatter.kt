package gi.aera.weather.presentation.formatter

import gi.aera.ui.text.UiString
import gi.aera.weather.Res
import gi.aera.weather.presentation.model.WeatherCode
import gi.aera.weather.weather_temperature_apparent
import kotlin.math.roundToInt

object WeatherConditionsFormatter {

  fun getWeatherConditionTitle(code: Int?) =
    code?.let { WeatherCode.Companion.getTitle(it) } ?: UiString.Empty

  fun getWeatherConditionIcon(code: Int?) =
    code?.let { WeatherCode.Companion.fromCode(it).conditionIcon } ?: WeatherCode.UNKNOWN.conditionIcon

  fun formatTemperatureApparent(temperatureApparent: Double?) =
    temperatureApparent?.let {
      UiString.Resource(Res.string.weather_temperature_apparent, it.roundToInt())
    } ?: UiString.Empty
}
