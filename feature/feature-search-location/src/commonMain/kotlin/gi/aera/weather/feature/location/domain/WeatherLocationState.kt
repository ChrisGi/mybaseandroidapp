package gi.aera.weather.feature.location.domain

import gi.aera.location.domain.model.SearchLocation
import gi.aera.ui.compose.SwipeableItem
import gi.aera.ui.text.UiString
import gi.aera.weather.Res
import gi.aera.weather.weather_location_temp_range

sealed interface WeatherLocationState {
  data class WeatherLocation(
    val canBeDeleted: Boolean = false,
    val location: UiString = UiString.Empty,
    val temperatureAvg: String = "",
    val temperatureMin: String = "",
    val temperatureMax: String = "",
    val condition: UiString = UiString.Empty,
    val searchLocation: SearchLocation,
  ) : WeatherLocationState, SwipeableItem {

    override fun isSwipeable(): Boolean = canBeDeleted

    val temperatureRangeFormatted: UiString
      get() = UiString.Resource(Res.string.weather_location_temp_range, temperatureMin, temperatureMax)
  }

  data class WeatherLocationError(
    override val message: String,
  ) : Throwable(), WeatherLocationState
}
