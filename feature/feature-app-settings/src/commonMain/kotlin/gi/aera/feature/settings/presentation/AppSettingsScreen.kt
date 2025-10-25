package gi.aera.feature.settings.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import gi.aera.appsettings.domain.model.UnitSystem
import gi.aera.feature.settings.presentation.model.UnitSettingsViewState
import gi.aera.weather.Res
import gi.aera.weather.settings_menu
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppSettingsScreen(
  state: UnitSettingsViewState,
  onOptionSelect: (UnitSystem) -> Unit,
  modifier: Modifier = Modifier,
  navigateBack: () -> Unit,
) {
  Scaffold(
    topBar = {
      MediumTopAppBar(
        title = { Text(text = stringResource(Res.string.settings_menu)) },
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
      UnitSettingsCard(
        state = state,
        modifier = Modifier
          .padding(16.dp),
        onOptionSelect = onOptionSelect,
      )
    }
  }
}
