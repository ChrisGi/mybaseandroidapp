package gi.aera.weathertomorrow

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import gi.aera.appsettings.domain.model.UnitSystem
import gi.aera.location.domain.model.LocationSource
import gi.aera.location.domain.model.SearchLocation
import gi.aera.ui.text.UiString
import gi.aera.ui.theme.AppTheme
import gi.aera.weathertomorrow.theme.appTypography
import gi.aera.weather.Res
import gi.aera.weather.domain.model.toTemperatureUnit
import gi.aera.weather.feature.location.domain.WeatherLocationState
import gi.aera.weather.feature.location.presentation.WeatherLocationCard
import gi.aera.weather.weather_code_1101

@Preview
@Composable
private fun WeatherCardPreview() {
  AppTheme(appTypography(), false) {
    Column(
      modifier = Modifier
        .background(MaterialTheme.colorScheme.background)
        .fillMaxSize()
        .padding(16.dp),
    ) {
      WeatherLocationCard(
        state = WeatherLocationState.WeatherLocation(
          location = UiString.Text("London"),
          temperature = "25",
          temperatureApparent = "16",
          temperatureUnit = UnitSystem.METRIC.toTemperatureUnit(),
          condition = UiString.Resource(Res.string.weather_code_1101),
          searchLocation = DEFAULT_LOCATION,
        ),
      )
    }
  }
}

@Suppress("MagicNumber")
private val DEFAULT_LOCATION =
  SearchLocation("PLACE_ID", "London", "London, UK", 51.509865, -0.118092, LocationSource.SEARCH)
