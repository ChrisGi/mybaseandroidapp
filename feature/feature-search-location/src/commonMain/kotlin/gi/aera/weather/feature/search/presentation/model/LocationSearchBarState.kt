package gi.aera.weather.feature.search.presentation.model

import gi.aera.ui.text.UiString

data class LocationSearchBarState(
  val queryValue: String,
  val expanded: Boolean,
  val placeholder: UiString,
)
