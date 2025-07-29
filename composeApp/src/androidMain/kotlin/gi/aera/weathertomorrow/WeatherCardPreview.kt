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
import gi.aera.ui.text.UiString
import gi.aera.ui.theme.AppTheme
import gi.aera.ui.theme.appTypography
import gi.aera.weather.feature.location.domain.WeatherLocation
import gi.aera.weather.feature.location.presentation.WeatherLocationCard

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
        state = WeatherLocation(
          location = UiString.Text("London"),
          temperatureAvg = "25",
          temperatureMin = "16°",
          temperatureMax = "26°",
          condition = UiString.Text("Trochę chmur"),
        ),
      )
    }
  }
}
