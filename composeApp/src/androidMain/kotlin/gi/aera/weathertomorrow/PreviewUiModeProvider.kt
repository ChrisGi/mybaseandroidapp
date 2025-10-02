package gi.aera.weathertomorrow

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

class PreviewUiModeProvider : PreviewParameterProvider<Boolean> {
  override val values = sequenceOf(
    false, true,
  )
}
