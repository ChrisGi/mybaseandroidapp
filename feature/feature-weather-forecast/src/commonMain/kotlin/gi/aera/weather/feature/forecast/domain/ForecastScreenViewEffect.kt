package gi.aera.weather.feature.forecast.domain

import gi.aera.ui.Event

sealed class ForecastScreenViewEffect : Event {
  data object SearchForLocation : ForecastScreenViewEffect()
}
