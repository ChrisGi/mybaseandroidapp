package gi.aera.weather.feature.search.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import gi.aera.location.domain.model.SearchLocation

@Composable
fun SearchLocationResults(
  results: List<SearchLocation>,
  isLoading: Boolean,
  save: (location: SearchLocation) -> Unit,
  modifier: Modifier = Modifier,
) {
  LazyColumn(
    modifier = modifier,
  ) {
    items(results) { location ->
      Column(
        modifier = Modifier
          .clickable {
            save(location)
          },
      ) {
        ListItem(
          headlineContent = {
            location.formatted?.let { Text(it, color = MaterialTheme.colorScheme.onSurface) }
          },
          colors = ListItemDefaults.colors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
          ),
        )
        HorizontalDivider(
          color = Color.LightGray,
        )
      }
    }
    item {
      if (isLoading) {
        Box(
          contentAlignment = Alignment.Center,
          modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        ) {
          CircularProgressIndicator()
        }
      }
    }
  }
}
