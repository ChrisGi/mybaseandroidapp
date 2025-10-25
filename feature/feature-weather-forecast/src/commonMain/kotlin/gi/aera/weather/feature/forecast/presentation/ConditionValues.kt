package gi.aera.weather.feature.forecast.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import gi.aera.weather.feature.forecast.presentation.model.ConditionValue
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
      LazyRow(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
          .fillMaxWidth(),
      ) {
        items(rowItems) { conditionValue ->
          val textColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
          Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
              .width(100.dp)
              .padding(top = 12.dp),
          ) {
            Image(
              colorFilter = ColorFilter.tint(textColor),
              painter = painterResource(conditionValue.icon),
              contentDescription = null,
              modifier = Modifier
                .size(24.dp)
                .align(Alignment.CenterHorizontally),
            )
            Text(
              style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
              text = conditionValue.value.asString(),
              modifier = Modifier
                .align(Alignment.CenterHorizontally),
            )
            Text(
              color = textColor,
              style = MaterialTheme.typography.bodySmall,
              text = conditionValue.description.asString().lowercase(),
              modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(horizontal = 8.dp),
            )
          }
        }
      }
    }
  }
}

private const val SHOW_3_ITEMS_IN_ROW = 3
