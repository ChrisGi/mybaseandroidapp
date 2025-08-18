package gi.aera.feature.settings.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import gi.aera.appsettings.domain.model.UnitSystem
import gi.aera.feature.settings.domain.model.UnitSettingsViewState
import gi.aera.weather.Res
import gi.aera.weather.settings_unit_system
import org.jetbrains.compose.resources.stringResource

@Composable
fun UnitSettingsCard(
  state: UnitSettingsViewState,
  modifier: Modifier = Modifier,
  onOptionSelect: (UnitSystem) -> Unit,
) {
  Column(
    modifier = modifier,
    verticalArrangement = Arrangement.spacedBy(16.dp),
  ) {
    Text(text = stringResource(Res.string.settings_unit_system))

    ElevatedCard(
      modifier = Modifier
        .fillMaxWidth(),
      shape = RoundedCornerShape(16.dp),
      elevation = CardDefaults.elevatedCardElevation(
        defaultElevation = 0.0.dp,
      ),
    ) {
      Column(
        modifier = Modifier
          .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
      ) {
        UnitSettingOptions(
          state = state,
          modifier = Modifier,
          onOptionSelect = onOptionSelect,
        )
      }
    }
  }
}
