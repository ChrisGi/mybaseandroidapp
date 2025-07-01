package gi.aera.weather.feature.location.domain.model

import gi.aera.location.domain.model.SearchLocation
import gi.aera.ui.Event

sealed interface SearchLocationEvent : Event {
  data class Save(val location: SearchLocation) : SearchLocationEvent
  data class Search(val query: String) : SearchLocationEvent
}
