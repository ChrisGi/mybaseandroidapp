package gi.aera.weather.feature.forecast.presentation.model

import gi.aera.ui.Event

sealed class ForecastScreenViewEffect : Event {
  data object SearchForLocation : ForecastScreenViewEffect()
}
