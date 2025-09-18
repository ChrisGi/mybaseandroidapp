package gi.aera.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Route {

  data object Up : Route

  data class SystemSettings(
    val settingType: SettingType,
  ) : Route

  @Serializable
  data object ForecastNavScreen : Route

  @Serializable
  data object SearchLocationNavScreen : Route
}

enum class SettingType {
  NETWORK,
}
