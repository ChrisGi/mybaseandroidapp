package gi.aera.weather.feature.forecast.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddLocationAlt
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp

@Composable
fun ForecastLocation(
  location: String,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  Row(
    horizontalArrangement = Arrangement.Center,
    verticalAlignment = Alignment.CenterVertically,
    modifier = modifier
      .clickable { onClick() },
  ) {
    Text(
      text = location,
      color = MaterialTheme.colorScheme.onBackground,
      style = MaterialTheme.typography.titleSmall,
      modifier = Modifier,
    )
    Image(
      colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground),
      imageVector = Icons.Outlined.AddLocationAlt,
      contentDescription = null,
      modifier = Modifier
        .size(32.dp)
        .padding(PaddingValues(horizontal = 6.dp)),
    )
  }
}
