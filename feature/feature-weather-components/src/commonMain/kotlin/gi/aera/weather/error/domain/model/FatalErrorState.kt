package gi.aera.weather.error.domain.model

import gi.aera.ui.text.UiString

data class FatalErrorState(
  val toolbarTitle: UiString = UiString.Empty,
  val message: UiString,
)
