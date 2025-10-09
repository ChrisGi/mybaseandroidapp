package gi.aera.weather.domain.model

import gi.aera.ui.text.UiString
import gi.aera.weather.Res
import gi.aera.weather.weather_temperature_apparent
import kotlin.test.Test
import kotlin.test.assertEquals

class WeatherConditionsFormatterTest {

  @Test
  fun `should return empty when weather code is not known`() {
    val expectValue = UiString.Empty
    val actualValue = WeatherConditionsFormatter.getWeatherConditionTitle(9999)

    assertEquals(expectValue, actualValue)
  }

  @Test
  fun `should return empty when weather code is null`() {
    val expectValue = UiString.Empty
    val actualValue = WeatherConditionsFormatter.getWeatherConditionTitle(null)

    assertEquals(expectValue, actualValue)
  }

  @Test
  fun `should return title when weather is clear and sunny`() {
    val clearSunny = WeatherCode.CLEAR_SUNNY
    val expectValue = UiString.Resource(clearSunny.conditionStringRes)
    val actualValue = WeatherConditionsFormatter.getWeatherConditionTitle(clearSunny.code)

    assertEquals(expectValue, actualValue)
  }

  @Test
  fun `should return empty temp apparent when weather code is not known`() {
    val expectValue = UiString.Empty
    val actualValue = WeatherConditionsFormatter.formatTemperatureApparent(null)

    assertEquals(expectValue, actualValue)
  }

  @Test
  fun `should return formatted and rounded temp apparent`() {
    val expectValue = UiString.Resource(Res.string.weather_temperature_apparent, 24)
    val actualValue = WeatherConditionsFormatter.formatTemperatureApparent(23.7)

    assertEquals(expectValue, actualValue)
  }
}
