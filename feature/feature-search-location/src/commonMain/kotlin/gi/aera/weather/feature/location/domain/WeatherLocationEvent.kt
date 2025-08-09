package gi.aera.weather.feature.location.domain

import gi.aera.location.domain.model.SearchLocation
import gi.aera.ui.Event

interface WeatherLocationEvent : Event {
  data class Save(val location: SearchLocation) : WeatherLocationEvent
  data class RemoveLocation(val location: SearchLocation) : WeatherLocationEvent
  data class SetAsDefault(val location: SearchLocation) : WeatherLocationEvent
}
