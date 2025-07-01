package gi.aera.weather.feature.location.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import gi.aera.weather.Res
import gi.aera.weather.feature.location.domain.model.SearchLocationEvent
import gi.aera.weather.feature.location.domain.model.SearchLocationViewState
import gi.aera.weather.search_location_label
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchLocationScreen(
  state: SearchLocationViewState,
  event: (event: SearchLocationEvent) -> Unit,
  navigateBack: () -> Unit,
  modifier: Modifier = Modifier
    .fillMaxSize(),
) {
  Scaffold(
    topBar = {
      MediumTopAppBar(
        title = { Text(text = state.toolbarTitle.asString()) },
        navigationIcon = {
          IconButton(onClick = navigateBack) {
            Icon(
              imageVector = Icons.AutoMirrored.Filled.ArrowBack,
              contentDescription = null,
            )
          }
        },
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
          .fillMaxSize()
          .padding(horizontal = 16.dp, vertical = 8.dp),
      ) {
        OutlinedTextField(
          leadingIcon = { Icon(imageVector = Icons.Filled.Search, contentDescription = null) },
          value = state.searchQuery,
          onValueChange = { newText -> event(SearchLocationEvent.Search(newText)) },
          label = { Text(stringResource(Res.string.search_location_label)) },
          singleLine = true,
          modifier = Modifier
            .fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(16.dp))

        if (state.isEmpty) {
          SearchLocationEmpty(
            modifier = Modifier
              .weight(1f)
              .align(Alignment.CenterHorizontally),
          )
        } else {
          SearchLocationResults(
            state, event, navigateBack,
            Modifier
              .weight(1f),
          )
        }
      }
    }
  }
}
