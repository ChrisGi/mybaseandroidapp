package gi.aera.weather.feature.location.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import gi.aera.weather.feature.location.domain.model.SearchLocationEvent
import gi.aera.weather.feature.location.domain.model.SearchLocationViewState

@Composable
fun SearchLocationResults(
  state: SearchLocationViewState,
  event: (event: SearchLocationEvent) -> Unit,
  navigateBack: () -> Unit,
  modifier: Modifier = Modifier,
) {
  LazyColumn(
    verticalArrangement = Arrangement.spacedBy(8.dp),
    modifier = modifier,
  ) {
    items(state.content) { location ->
      Column(
        modifier = Modifier
          .clickable {
            event(SearchLocationEvent.Save(location))
            navigateBack()
          },
      ) {
        location.formatted?.let { Text(text = it) }
        Spacer(modifier = Modifier.height(8.dp))
        HorizontalDivider()
      }
    }
    item {
      if (state.isLoading) {
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
