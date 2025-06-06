package gi.aera.weather.feature.forecast.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

@Composable
fun Temperature(
  temperature: String,
  unit: String = "C",
  fontSize: Int = 10,
) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier.fillMaxWidth(),
  ) {
    Text(
      color = MaterialTheme.colorScheme.onBackground,
      text = temperature,
      fontSize = (fontSize * 12).sp,
      modifier = Modifier,
    )
    Column {
      Text(
        color = MaterialTheme.colorScheme.onBackground,
        text = "°",
        fontSize = (fontSize * 6).sp,
      )
      Text(
        color = MaterialTheme.colorScheme.onBackground,
        text = unit,
        fontSize = (fontSize * 3).sp,
        modifier = Modifier.align(Alignment.CenterHorizontally),
      )
    }
  }
}
