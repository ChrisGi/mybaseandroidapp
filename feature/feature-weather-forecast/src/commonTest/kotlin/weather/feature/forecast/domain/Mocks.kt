package weather.feature.forecast.domain

import gi.aera.location.domain.model.LocationSource
import gi.aera.location.domain.model.SearchLocation

internal const val CITY = "Warsaw"
internal val fakeSearchLocation = SearchLocation(
  placeId = "1234",
  city = CITY,
  formatted = null,
  latitude = 1.2,
  longitude = 1.5,
  source = LocationSource.SEARCH,
)
