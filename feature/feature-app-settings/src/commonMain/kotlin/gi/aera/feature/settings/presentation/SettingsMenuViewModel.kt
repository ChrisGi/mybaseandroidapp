package gi.aera.feature.settings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gi.aera.feature.settings.presentation.model.SettingMenuItem
import gi.aera.feature.settings.presentation.model.SettingMenuItemId
import gi.aera.feature.settings.presentation.model.SettingMenuViewEffect
import gi.aera.feature.settings.presentation.model.SettingsMenuViewState
import gi.aera.feature.settings.presentation.model.toTitle
import gi.aera.ui.text.UiString
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsMenuViewModel : ViewModel() {

  private val _settingsMenuViewState = MutableStateFlow(SettingsMenuViewState())
  val settingsMenuViewState: StateFlow<SettingsMenuViewState> = _settingsMenuViewState
    .onStart { provideSettings() }
    .stateIn(
      viewModelScope,
      SharingStarted.Eagerly,
      SettingsMenuViewState(),
    )

  private val _settingsMenuViewEffect = MutableSharedFlow<SettingMenuViewEffect>()
  val settingsMenuViewEffect = _settingsMenuViewEffect.asSharedFlow()

  private fun provideSettings() {
    val menu = SettingMenuItemId.entries.map {
      SettingMenuItem(
        UiString.Resource(it.toTitle()),
        it,
      )
    }
    _settingsMenuViewState.value = SettingsMenuViewState(menu)
  }

  fun obtainMenuClick(settingMenuItemId: SettingMenuItemId) {
    when (settingMenuItemId) {
      SettingMenuItemId.SETTINGS -> showSettingsScreen()
    }
  }

  private fun showSettingsScreen() = viewModelScope.launch {
    _settingsMenuViewEffect.emit(SettingMenuViewEffect.ShowSettingsScreen)
  }
}
