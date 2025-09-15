package gi.aera.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Route {

  data object Up : Route

  @Serializable
  data object ForecastNavScreen : Route

  @Serializable
  data object SearchLocationNavScreen : Route

  @Serializable
  data class FatalErrorNavScreen(
    val message: String,
  ) : Route

  @Serializable
  data class NetworkErrorNavScreen(
    val errorCode: Int,
  ) : Route
}
