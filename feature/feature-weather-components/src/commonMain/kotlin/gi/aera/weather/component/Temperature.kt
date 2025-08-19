package gi.aera.weather.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import gi.aera.appsettings.domain.model.UnitSystem
import gi.aera.weather.domain.model.toTemperatureScale

@Suppress("ComposableParamOrder")
@Composable
fun Temperature(
  temperature: String,
  unit: UnitSystem,
  fontSize: Int = 10,
  modifier: Modifier = Modifier,
) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = modifier,
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
        text = unit.toTemperatureScale(),
        fontSize = (fontSize * 3).sp,
        modifier = Modifier.align(Alignment.CenterHorizontally),
      )
    }
  }
}
