package gi.aera.weather.feature.settings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gi.aera.ui.text.UiString
import gi.aera.weather.feature.settings.domain.SettingMenuItem
import gi.aera.weather.feature.settings.domain.SettingMenuItemId
import gi.aera.weather.feature.settings.domain.SettingMenuViewEffect
import gi.aera.weather.feature.settings.domain.SettingsMenuViewState
import gi.aera.weather.feature.settings.domain.toTitle
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
