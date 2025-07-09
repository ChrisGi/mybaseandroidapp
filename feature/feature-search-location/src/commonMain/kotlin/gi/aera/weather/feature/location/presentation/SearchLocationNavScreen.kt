package gi.aera.weather.feature.location.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel

fun NavGraphBuilder.searchLocationScreen(
  navigateBack: () -> Unit,
) {
  composable<SearchLocationNavScreen> {
    val viewModel = koinViewModel<SearchLocationViewModel>()
    val state by viewModel.searchLocationViewState.collectAsStateWithLifecycle()

    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      modifier = Modifier
        .background(MaterialTheme.colorScheme.background)
        .fillMaxSize(),
    ) {
      SearchLocationScreen(
        state,
        viewModel::obtainEvent,
        navigateBack,
      )
    }
  }
}

@Serializable
data object SearchLocationNavScreen
