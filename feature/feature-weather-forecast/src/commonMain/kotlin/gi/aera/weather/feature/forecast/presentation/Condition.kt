package gi.aera.weather.feature.forecast.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import gi.aera.weather.Res

@Composable
fun Condition(
  icon: String,
  description: String,
  modifier: Modifier = Modifier,
) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = modifier,
  ) {
    if (!LocalInspectionMode.current) {
      AsyncImage(
        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground),
        model = Res.getUri(icon),
        contentDescription = null,
        modifier = Modifier
          .fillMaxSize()
          .align(Alignment.CenterVertically)
          .weight(1f)
          .padding(32.dp),
      )
    }

    Column {
      description
        .uppercase()
        .forEach { char ->
          Text(
            color = MaterialTheme.colorScheme.onBackground,
            text = char.toString(),
            style = TextStyle(fontWeight = FontWeight.Bold),
            modifier = Modifier
              .align(Alignment.CenterHorizontally),
          )
        }
    }
  }
}
