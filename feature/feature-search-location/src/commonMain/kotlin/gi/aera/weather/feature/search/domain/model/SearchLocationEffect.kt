package gi.aera.weather.feature.search.domain.model

import gi.aera.location.domain.model.SearchLocation
import gi.aera.ui.Effect

sealed interface SearchLocationEffect : Effect {
  data class ShowLocationWeather(val location: SearchLocation) : SearchLocationEffect
}
