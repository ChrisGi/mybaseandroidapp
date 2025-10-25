package gi.aera.weather.presentation.formatter

import gi.aera.location.domain.model.SearchLocation
import gi.aera.ui.text.UiString
import gi.aera.weather.Res
import gi.aera.weather.location_current

fun SearchLocation.formatLocation() = when {
  city != null -> UiString.Text(city!!)
  formatted != null -> UiString.Text(formatted!!)
  else -> UiString.Resource(Res.string.location_current)
}
