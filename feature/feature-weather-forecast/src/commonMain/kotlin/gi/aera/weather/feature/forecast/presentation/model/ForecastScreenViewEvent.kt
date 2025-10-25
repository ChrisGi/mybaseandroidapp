package gi.aera.weather.feature.forecast.presentation.model

import gi.aera.ui.Event

interface ForecastScreenViewEvent : Event {
  data class NavigateToSearchLocation(val popUpInclusive: Boolean = false) : ForecastScreenViewEvent
  data object Retry : ForecastScreenViewEvent
  data object NavigateToNetworkSettings : ForecastScreenViewEvent
}
