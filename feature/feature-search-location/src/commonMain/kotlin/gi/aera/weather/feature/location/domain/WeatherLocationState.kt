package gi.aera.weather.feature.location.domain

import gi.aera.domain.model.AppError
import gi.aera.location.domain.model.SearchLocation
import gi.aera.ui.compose.SwipeableItem
import gi.aera.ui.text.UiString
import gi.aera.weather.Res
import gi.aera.weather.weather_temperature_apparent

sealed interface WeatherLocationState {
  data class WeatherLocation(
    val canBeDeleted: Boolean = false,
    val location: UiString = UiString.Empty,
    val temperature: String = "",
    val temperatureApparent: String = "",
    val temperatureUnit: String = "",
    val condition: UiString = UiString.Empty,
    val searchLocation: SearchLocation,
  ) : WeatherLocationState, SwipeableItem {

    override fun isSwipeable(): Boolean = canBeDeleted

    val temperatureApparentFormatted: UiString
      get() = UiString.Resource(Res.string.weather_temperature_apparent, temperatureApparent)
  }

  data class WeatherLocationError(
    val appError: AppError,
  ) : WeatherLocationState
}
