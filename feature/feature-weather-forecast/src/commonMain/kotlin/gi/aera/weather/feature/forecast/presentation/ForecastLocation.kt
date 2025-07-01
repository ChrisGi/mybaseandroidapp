package gi.aera.weather.feature.forecast.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
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

@Composable
fun ForecastLocation(
  location: String,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  Row(
    horizontalArrangement = Arrangement.Center,
    modifier = modifier
      .clickable { onClick() },
  ) {
    Text(
      text = location.uppercase(),
      color = MaterialTheme.colorScheme.onBackground,
      style = TextStyle(fontWeight = FontWeight.Bold),
      modifier = Modifier
        .align(Alignment.CenterVertically),
    )
    if (!LocalInspectionMode.current) {
      Image(
        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground),
        imageVector = Icons.Outlined.LocationOn,
        contentDescription = null,
        modifier = Modifier
          .size(32.dp)
          .padding(PaddingValues(horizontal = 4.dp)),
      )
    }
  }
}
