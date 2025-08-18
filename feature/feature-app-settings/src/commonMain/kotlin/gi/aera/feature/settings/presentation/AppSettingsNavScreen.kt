package gi.aera.feature.settings.presentation

import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel

fun NavGraphBuilder.appSettingsNavScreen(
  navigateBack: () -> Unit = {},
) {
  composable<AppSettingsNavScreen> {

    val viewModel: UnitSystemSettingViewModel = koinViewModel()
    val state by viewModel.unitSettingsViewState.collectAsStateWithLifecycle()

    AppSettingsScreen(
      state = state,
      onOptionSelect = viewModel::onOptionSelected,
      modifier = Modifier,
      navigateBack = navigateBack,
    )
  }
}

@Serializable
data object AppSettingsNavScreen
