package gi.aera.weather.feature.forecast.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import gi.aera.weather.component.Temperature
import gi.aera.weather.feature.forecast.presentation.model.WeatherConditions
import org.jetbrains.compose.resources.painterResource

@Composable
fun Condition(
  state: WeatherConditions,
  modifier: Modifier = Modifier,
) {
  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = modifier,
  ) {
    Image(
      painter = painterResource(state.conditionIcon),
      contentDescription = null,
      modifier = Modifier
        .size(168.dp),
    )
    Text(
      text = state.conditionTitle.asString(),
      style = MaterialTheme.typography.bodyLarge,
    )
    Temperature(
      temperature = state.temperature,
      unit = state.temperatureUnit,
    )
    Text(
      text = state.temperatureApparent.asString(),
      style = MaterialTheme.typography.bodySmall,
    )
  }
}
