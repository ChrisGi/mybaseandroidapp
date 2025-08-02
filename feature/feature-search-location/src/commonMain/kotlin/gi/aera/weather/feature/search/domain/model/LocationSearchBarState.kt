package gi.aera.weather.feature.search.domain.model

import gi.aera.ui.text.UiString

data class LocationSearchBarState(
  val queryValue: String,
  val expanded: Boolean,
  val placeholder: UiString,
)
