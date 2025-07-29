package gi.aera.weather.feature.location.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import gi.aera.location.domain.model.SearchLocation
import gi.aera.weather.feature.location.domain.WeatherLocation

@Composable
fun WeatherLocationList(
  data: List<WeatherLocation>,
  modifier: Modifier = Modifier,
  selectLocation: (location: SearchLocation) -> Unit = {},
) {
  LazyColumn(
    contentPadding = PaddingValues(16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp),
    modifier = modifier,
  ) {
    items(data) { weatherLocation ->
      WeatherLocationCard(
        state = weatherLocation,
        selectLocation = selectLocation,
      )
    }
  }
}
