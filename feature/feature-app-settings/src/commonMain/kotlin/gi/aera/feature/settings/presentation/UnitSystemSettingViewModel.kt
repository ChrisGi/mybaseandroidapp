package gi.aera.feature.settings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gi.aera.appsettings.domain.model.UnitSystem
import gi.aera.appsettings.domain.usecase.GetUnitSettingsUseCase
import gi.aera.appsettings.domain.usecase.SaveUnitSettingsUseCase
import gi.aera.feature.settings.domain.model.UnitSettingsViewState
import gi.aera.feature.settings.domain.model.UnitSystemItem
import gi.aera.feature.settings.domain.model.toTitle
import gi.aera.ui.text.UiString
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class UnitSystemSettingViewModel(
  private val getUnitSystemSettingUseCase: GetUnitSettingsUseCase,
  private val saveUnitSystemSettingUseCase: SaveUnitSettingsUseCase,
) : ViewModel() {

  private val _unitSettingsViewState = MutableStateFlow(UnitSettingsViewState())
  val unitSettingsViewState = _unitSettingsViewState
    .onStart { provideUnitSystemSettingViewState() }
    .stateIn(
      scope = viewModelScope,
      started = SharingStarted.Eagerly,
      initialValue = UnitSettingsViewState(),
    )

  private fun provideUnitSystemSettingViewState() = viewModelScope.launch {
    getUnitSystemSettingUseCase()
      .map { selectedUnit ->
        UnitSystem.entries.map { unitSystem ->
          UnitSystemItem(
            unitSystem,
            unitSystem == selectedUnit,
            UiString.Resource(unitSystem.toTitle()),
          )
        }
      }
      .catch { e -> e.printStackTrace() }
      .collect {
        _unitSettingsViewState.value = UnitSettingsViewState(it)
      }
  }

  fun onOptionSelected(unitSystem: UnitSystem) = viewModelScope.launch {
    saveUnitSystemSettingUseCase(unitSystem)
      .onFailure { println("Failed to save unit system") }
  }
}
