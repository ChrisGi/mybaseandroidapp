package gi.aera.weather.feature.search.presentation.model

import gi.aera.location.domain.model.SearchLocation
import gi.aera.ui.Event

sealed interface SearchLocationEvent : Event {
  data class Search(val query: String) : SearchLocationEvent
  data class ShowLocationWeather(val location: SearchLocation) : SearchLocationEvent
  data object ClearSearch : SearchLocationEvent
  data object NavigateToNetworkSettings : SearchLocationEvent
  data object RetrySearch : SearchLocationEvent
}
