package gi.aera.weather.feature.search.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SearchLocationEmpty(modifier: Modifier = Modifier) {
  Box(modifier = modifier) {
    Text(
      text = "No locations found",
      modifier = Modifier
        .align(Alignment.Center)
        .padding(16.dp),
    )
  }
}
