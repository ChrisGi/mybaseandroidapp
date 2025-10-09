package gi.aera.weather.domain.model

import gi.aera.location.domain.model.LocationSource
import gi.aera.location.domain.model.SearchLocation
import gi.aera.ui.text.UiString
import gi.aera.weather.Res
import gi.aera.weather.location_current
import kotlin.test.Test
import kotlin.test.assertEquals

class SearchLocationFormatterTest {

  @Test
  fun `should give location title when city and formatted are null`() {
    val fakeSearchLocation = SearchLocation(
      placeId = "1234",
      city = null,
      formatted = null,
      latitude = 1.2,
      longitude = 1.5,
      source = LocationSource.SEARCH,
    )

    val expectLocationTitle = UiString.Resource(Res.string.location_current)
    assertEquals(expectLocationTitle, fakeSearchLocation.formatLocation())
  }

  @Test
  fun `should give location title when city is not null`() {
    val location = SearchLocation(
      placeId = "1234",
      city = "Warsaw",
      formatted = "Warsaw, Poland",
      latitude = 1.2,
      longitude = 1.5,
      source = LocationSource.SEARCH,
    )

    val expected = UiString.Text("Warsaw")
    assertEquals(expected, location.formatLocation())
  }

  @Test
  fun `should give location title when city is null and formatted is not null`() {
    val location = SearchLocation(
      placeId = "1234",
      city = null,
      formatted = "Poland",
      latitude = 1.2,
      longitude = 1.5,
      source = LocationSource.SEARCH,
    )

    val expected = UiString.Text("Poland")
    assertEquals(expected, location.formatLocation())
  }
}
