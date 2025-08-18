package gi.aera.feature.settings.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import gi.aera.appsettings.domain.model.UnitSystem
import gi.aera.feature.settings.domain.model.UnitSettingsViewState

@Composable
fun UnitSettingOptions(
  state: UnitSettingsViewState,
  modifier: Modifier = Modifier,
  onOptionSelect: (UnitSystem) -> Unit,
) {
  Column(modifier.selectableGroup()) {
    state.unitSystemOptions.forEach { unitOption ->
      Row(
        Modifier
          .fillMaxWidth()
          .height(42.dp)
          .selectable(
            selected = unitOption.isSelected,
            onClick = { onOptionSelect(unitOption.unitSystem) },
            role = Role.RadioButton,
          )
          .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
      ) {
        RadioButton(
          selected = unitOption.isSelected,
          onClick = null,
        )
        Text(
          text = unitOption.title.asString(),
          modifier = Modifier.padding(start = 16.dp),
        )
      }
    }
  }
}
