@file:Suppress("MagicNumber")

package gi.aera.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val backgroundGradientHigh = Color(0xff90D5FF)
val backgroundGradientMid = Color(0xffcce3ed)
val backgroundGradientLow = Color(0xffffffff)

val backgroundGradientHighDark = Color(0xff272757)
val backgroundGradientMidDark = Color(0xff4242b6)
val backgroundGradientLowDark = Color(0xff8686AC)

val backgroundGradientLight = Brush.linearGradient(
  colorStops = arrayOf(
    0.0f to backgroundGradientHigh,
    0.5f to backgroundGradientMid,
    1.0f to backgroundGradientLow,
  ),
)

val backgroundGradientDark = Brush.linearGradient(
  colorStops = arrayOf(
    0.0f to backgroundGradientHighDark,
    0.5f to backgroundGradientMidDark,
    1.0f to backgroundGradientLowDark,
  ),
)

val surfaceGradientLight = Brush.verticalGradient(
  colorStops = arrayOf(
    0.5f to Color.Transparent,
    1f to backgroundGradientLow,
  ),
)

val surfaceGradientDark = Brush.verticalGradient(
  colorStops = arrayOf(
    0.5f to Color.Transparent,
    1f to backgroundGradientLowDark,
  ),
)

val surfaceGradientReversedLight = Brush.verticalGradient(
  colorStops = arrayOf(
    0f to backgroundGradientLow,
    1f to Color.Transparent,
  ),
)

val surfaceGradientReversedDark = Brush.verticalGradient(
  colorStops = arrayOf(
    0f to backgroundGradientLowDark,
    1f to Color.Transparent,
  ),
)

data class ExtraColors(
  val backgroundGradient: Brush = backgroundGradientLight,
  val surfaceGradient: Brush = surfaceGradientLight,
  val surfaceGradientReversed: Brush = surfaceGradientReversedLight,
)

val lightExtraColors = ExtraColors(
  backgroundGradient = backgroundGradientLight,
  surfaceGradient = surfaceGradientLight,
  surfaceGradientReversed = surfaceGradientReversedLight,
)

val darkExtraColors = ExtraColors(
  backgroundGradient = backgroundGradientDark,
  surfaceGradient = surfaceGradientDark,
  surfaceGradientReversed = surfaceGradientReversedDark,
)

@Suppress("CompositionLocalAllowlist")
val LocalExtraColors = compositionLocalOf { ExtraColors() }

val MaterialTheme.extraColors: ExtraColors
  @Composable
  @ReadOnlyComposable
  get() = LocalExtraColors.current
