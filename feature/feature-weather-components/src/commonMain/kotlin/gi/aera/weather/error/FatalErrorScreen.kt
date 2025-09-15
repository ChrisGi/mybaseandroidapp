package gi.aera.weather.error

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import gi.aera.weather.error.domain.model.FatalErrorState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FatalErrorScreen(
  state: FatalErrorState,
  modifier: Modifier = Modifier
    .fillMaxSize(),
  navigateBack: () -> Unit,
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
    Column(Modifier.padding(innerPadding)) {
      FatalError(
        message = state.message,
        modifier = Modifier
          .fillMaxSize()
          .padding(32.dp),
      )
    }
  }
}
