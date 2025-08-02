package gi.aera.weather.feature.search.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import gi.aera.weather.feature.search.domain.model.SearchLocationEvent
import gi.aera.weather.feature.search.domain.model.SearchLocationViewState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchLocationScreen(
  state: SearchLocationViewState,
  event: (event: SearchLocationEvent) -> Unit,
  modifier: Modifier = Modifier
    .fillMaxSize(),
  content: @Composable () -> Unit,
) {
  Scaffold(
    topBar = {
      MediumTopAppBar(
        title = { Text(text = state.toolbarTitle.asString()) },
      )
    },
    modifier = modifier,
  ) { innerPadding ->
    Surface(
      modifier = Modifier
        .padding(innerPadding),
    ) {
      Column(
        modifier = Modifier
          .fillMaxSize(),
      ) {
        SearchLocationBar(
          state = state.locationSearchBarState,
          search = { event(SearchLocationEvent.Search(it)) },
        ) {
          if (state.isEmpty) {
            SearchLocationEmpty(
              modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterHorizontally),
            )
          } else {
            SearchLocationResults(
              state.content,
              state.isLoading,
              { event(SearchLocationEvent.ShowLocationWeather(it)) },
              Modifier
                .weight(1f),
            )
          }
        }

        content()
      }
    }
  }
}
