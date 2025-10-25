package gi.aera.weather.feature.location.presentation.model

import gi.aera.common.model.AppError
import gi.aera.location.domain.model.SearchLocation
import gi.aera.ui.compose.SwipeableItem
import gi.aera.ui.text.UiString

sealed interface WeatherLocationState {
  data class WeatherLocation(
    val canBeDeleted: Boolean = false,
    val location: UiString = UiString.Empty,
    val temperature: String = "",
    val temperatureApparent: UiString = UiString.Empty,
    val temperatureUnit: String = "",
    val condition: UiString = UiString.Empty,
    val searchLocation: SearchLocation,
  ) : WeatherLocationState, SwipeableItem {

    override fun isSwipeable(): Boolean = canBeDeleted
  }

  data class WeatherLocationError(
    val appError: AppError,
  ) : WeatherLocationState
}
