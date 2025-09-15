package gi.aera.weather.feature.forecast.domain

import gi.aera.ui.Event

interface ForecastScreenViewEvent : Event {
  data class NavigateToSearchLocation(val popUpInclusive: Boolean = false) : ForecastScreenViewEvent
  data object Retry : ForecastScreenViewEvent
}
