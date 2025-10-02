package gi.aera.weather.feature.forecast.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import gi.aera.weather.feature.forecast.domain.ConditionValue
import org.jetbrains.compose.resources.painterResource

@Composable
fun ConditionValues(
  state: List<ConditionValue>,
  modifier: Modifier = Modifier,
) {
  Column(
    verticalArrangement = Arrangement.Bottom,
    modifier = modifier,
  ) {
    state.chunked(SHOW_3_ITEMS_IN_ROW).forEach { rowItems ->
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
      ) {
        rowItems.forEach { conditionValue ->
          Box(
            modifier = Modifier
              .padding(4.dp),
          ) {
            Column(
              horizontalAlignment = Alignment.CenterHorizontally,
              modifier = Modifier
                .width(100.dp)
                .padding(8.dp),
            ) {
              Image(
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground),
                painter = painterResource(conditionValue.icon),
                contentDescription = null,
                modifier = Modifier
                  .size(32.dp)
                  .align(Alignment.CenterHorizontally),
              )
              Text(
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                text = conditionValue.value.asString(),
                modifier = Modifier
                  .align(Alignment.CenterHorizontally),
              )
              Text(
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodySmall,
                text = conditionValue.description.asString().lowercase(),
                modifier = Modifier
                  .align(Alignment.CenterHorizontally),
              )
            }
          }
        }
      }
    }
  }
}

private const val SHOW_3_ITEMS_IN_ROW = 3
