package gi.aera.weather.feature.forecast.domain

import gi.aera.ui.Event

sealed class ForecastScreenViewEvent : Event {
  data object NavigateToSearchLocation : ForecastScreenViewEvent()
}
