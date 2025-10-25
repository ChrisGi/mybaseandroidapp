package gi.aera.weather.feature.location.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import gi.aera.location.domain.model.SearchLocation
import gi.aera.weather.component.Temperature
import gi.aera.weather.feature.location.presentation.model.WeatherLocationState

@Composable
fun WeatherLocationCard(
  state: WeatherLocationState.WeatherLocation,
  modifier: Modifier = Modifier,
  selectLocation: (location: SearchLocation) -> Unit = {},
) {
  ElevatedCard(
    onClick = { selectLocation(state.searchLocation) },
    modifier = modifier,
    shape = RoundedCornerShape(16.dp),
  ) {
    Box(
      modifier = Modifier
        .height(CARD_HEIGHT)
        .padding(16.dp),
    ) {
      Row(
        modifier = Modifier,
      ) {
        Column(
          verticalArrangement = Arrangement.SpaceBetween,
          modifier = Modifier
            .fillMaxHeight()
            .weight(SPLIT_SCREEN_HALF),
        ) {
          Text(
            text = state.location.asString(),
            style = MaterialTheme.typography.titleLarge.copy(
              fontWeight = FontWeight.SemiBold,
            ),
          )
          Text(
            text = state.condition.asString(),
            style = MaterialTheme.typography.bodyMedium,
          )
        }

        Column(
          verticalArrangement = Arrangement.SpaceBetween,
          horizontalAlignment = Alignment.End,
          modifier = Modifier
            .fillMaxHeight()
            .weight(SPLIT_SCREEN_HALF),
        ) {
          Temperature(
            temperature = state.temperature,
            unit = state.temperatureUnit,
            fontSize = 4,
          )
          Text(
            text = state.temperatureApparent.asString(),
            style = MaterialTheme.typography.bodyMedium,
          )
        }
      }
    }
  }
}

private const val SPLIT_SCREEN_HALF = 0.5f
private val CARD_HEIGHT = 150.dp
