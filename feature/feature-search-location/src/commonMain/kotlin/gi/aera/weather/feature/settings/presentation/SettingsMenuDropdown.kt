package gi.aera.weather.feature.settings.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import gi.aera.weather.feature.settings.presentation.model.SettingMenuItem
import gi.aera.weather.feature.settings.presentation.model.SettingMenuItemId

@Composable
fun SettingsDropdown(
  state: List<SettingMenuItem>,
  modifier: Modifier = Modifier,
  onClick: (SettingMenuItemId) -> Unit = {},
) {
  var expanded by remember { mutableStateOf(false) }
  Box(
    modifier = modifier.padding(16.dp),
  ) {
    IconButton(onClick = { expanded = !expanded }) {
      Icon(Icons.Default.MoreVert, contentDescription = null)
    }
    DropdownMenu(
      expanded = expanded,
      onDismissRequest = { expanded = false },
    ) {
      for (item in state) {
        key(item.id) {
          DropdownMenuItem(
            text = { Text(item.title.asString(), color = MaterialTheme.colorScheme.onSurface) },
            onClick = {
              expanded = false
              onClick(item.id)
            },
          )
        }
      }
    }
  }
}
