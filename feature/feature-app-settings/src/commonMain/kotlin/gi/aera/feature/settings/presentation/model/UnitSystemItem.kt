package gi.aera.feature.settings.presentation.model

import gi.aera.appsettings.domain.model.UnitSystem
import gi.aera.ui.text.UiString

data class UnitSystemItem(
  val unitSystem: UnitSystem,
  val isSelected: Boolean = false,
  val title: UiString,
)
