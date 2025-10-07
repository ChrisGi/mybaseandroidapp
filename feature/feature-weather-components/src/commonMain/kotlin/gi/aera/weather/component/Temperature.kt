package gi.aera.weather.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

@Composable
fun Temperature(
  temperature: String,
  unit: String,
  modifier: Modifier = Modifier,
  fontSize: Int = 10,
) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = modifier,
  ) {
    Text(
      text = temperature,
      fontSize = (fontSize * 12).sp,
      modifier = Modifier,
    )
    Column {
      Text(
        text = "°",
        fontSize = (fontSize * 6).sp,
      )
      Text(
        text = unit,
        fontSize = (fontSize * 3).sp,
        modifier = Modifier.align(Alignment.CenterHorizontally),
      )
    }
  }
}
