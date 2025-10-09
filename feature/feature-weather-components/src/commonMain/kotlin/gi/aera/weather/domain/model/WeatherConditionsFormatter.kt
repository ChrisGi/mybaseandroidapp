package gi.aera.weather.domain.model

import gi.aera.ui.text.UiString
import gi.aera.weather.Res
import gi.aera.weather.weather_temperature_apparent
import kotlin.math.roundToInt

object WeatherConditionsFormatter {

  fun getWeatherConditionTitle(code: Int?) =
    code?.let { WeatherCode.getTitle(it) } ?: UiString.Empty

  fun getWeatherConditionIcon(code: Int?) =
    code?.let { WeatherCode.fromCode(it).conditionIcon } ?: WeatherCode.UNKNOWN.conditionIcon

  fun formatTemperatureApparent(temperatureApparent: Double?) =
    temperatureApparent?.let {
      UiString.Resource(Res.string.weather_temperature_apparent, it.roundToInt())
    } ?: UiString.Empty
}
