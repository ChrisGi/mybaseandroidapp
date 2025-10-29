package gi.aera.weather.feature.search.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import gi.aera.ui.LceViewState
import gi.aera.weather.error.AppErrorContentProvider
import gi.aera.weather.feature.search.presentation.model.SearchLocationEvent
import gi.aera.weather.feature.search.presentation.model.SearchLocationViewState
import gi.aera.weather.feature.settings.presentation.model.SettingMenuItemId
import gi.aera.weather.feature.settings.presentation.model.SettingsMenuViewState
import gi.aera.weather.feature.settings.presentation.SettingsDropdown

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchLocationScreen(
  state: SearchLocationViewState,
  menuState: SettingsMenuViewState,
  event: (event: SearchLocationEvent) -> Unit,
  menuEvent: (SettingMenuItemId) -> Unit,
  modifier: Modifier = Modifier.fillMaxSize(),
  content: @Composable () -> Unit,
) {
  Scaffold(
    topBar = {
      TopAppBar(
        title = { Text(text = state.toolbarTitle.asString()) },
        actions = { SettingsDropdown(menuState.settingsMenuState) { menuEvent(it) } },
      )
    },
    modifier = modifier,
  ) { innerPadding ->
    Box(
      modifier = Modifier
        .padding(innerPadding),
    ) {
      Column(
        modifier = Modifier.fillMaxSize(),
      ) {
        SearchLocationBar(
          state = state.locationSearchBarState,
          search = { event(SearchLocationEvent.Search(it)) },
          clearSearch = { event(SearchLocationEvent.ClearSearch) },
        ) {
          if (state.isEmpty) {
            SearchLocationEmpty(
              modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterHorizontally),
            )
          } else {
            if (state.displayState != null) {
              LceViewState(
                state = state.displayState,
                modifier = Modifier.weight(1f),
                errorContent = { appError ->
                  AppErrorContentProvider(
                    appError,
                    Modifier
                      .fillMaxSize()
                      .padding(32.dp),
                    onCheckNetwork = { event(SearchLocationEvent.NavigateToNetworkSettings) },
                    onRetry = { event(SearchLocationEvent.RetrySearch) },
                  )
                },
              ) { content ->
                SearchLocationResults(
                  state.content,
                  state.isLoading,
                  { event(SearchLocationEvent.ShowLocationWeather(it)) },
                  Modifier.weight(1f),
                )
              }
            }
          }
        }
        content()
      }
    }
  }
}
