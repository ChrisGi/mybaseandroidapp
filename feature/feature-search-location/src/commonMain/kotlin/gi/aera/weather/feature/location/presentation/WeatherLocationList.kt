package gi.aera.weather.feature.location.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import gi.aera.location.domain.model.SearchLocation
import gi.aera.ui.compose.SwipeToDeleteContainer
import gi.aera.weather.feature.location.presentation.model.WeatherLocationState

@Composable
fun WeatherLocationList(
  data: List<WeatherLocationState.WeatherLocation>,
  modifier: Modifier = Modifier,
  onLocationDelete: (SearchLocation) -> Unit = {},
  onLocationSet: (SearchLocation) -> Unit = {},
) {
  LazyColumn(
    contentPadding = PaddingValues(16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp),
    modifier = modifier,
  ) {
    items(
      items = data,
      key = { it.searchLocation.placeId },
    ) { weatherLocation ->
      SwipeToDeleteContainer(
        item = weatherLocation,
        onDelete = { onLocationDelete(it.searchLocation) },
      ) {
        WeatherLocationCard(
          state = weatherLocation,
          selectLocation = { onLocationSet(it) },
        )
      }
    }
  }
}
