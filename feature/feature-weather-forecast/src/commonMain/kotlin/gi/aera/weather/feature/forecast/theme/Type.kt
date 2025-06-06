package gi.aera.weather.feature.forecast.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import gi.aera.weather.Afacad_Bold
import gi.aera.weather.Afacad_Medium
import gi.aera.weather.Afacad_Regular
import gi.aera.weather.Afacad_SemiBold
import gi.aera.weather.Res
import org.jetbrains.compose.resources.Font

@Composable
fun AppFontFamily() = FontFamily(
  Font(Res.font.Afacad_Regular, weight = FontWeight.Normal),
  Font(Res.font.Afacad_Medium, weight = FontWeight.Medium),
  Font(Res.font.Afacad_SemiBold, weight = FontWeight.SemiBold),
  Font(Res.font.Afacad_Bold, weight = FontWeight.Bold)
)

val baseline = Typography()

@Composable
fun AppTypography(): Typography {
  val appFontFamily = AppFontFamily()
  return Typography(
    displayLarge = baseline.displayLarge.copy(fontFamily = appFontFamily),
    displayMedium = baseline.displayMedium.copy(fontFamily = appFontFamily),
    displaySmall = baseline.displaySmall.copy(fontFamily = appFontFamily),
    headlineLarge = baseline.headlineLarge.copy(fontFamily = appFontFamily),
    headlineMedium = baseline.headlineMedium.copy(fontFamily = appFontFamily),
    headlineSmall = baseline.headlineSmall.copy(fontFamily = appFontFamily),
    titleLarge = baseline.titleLarge.copy(fontFamily = appFontFamily),
    titleMedium = baseline.titleMedium.copy(fontFamily = appFontFamily),
    titleSmall = baseline.titleSmall.copy(fontFamily = appFontFamily),
    bodyLarge = baseline.bodyLarge.copy(fontFamily = appFontFamily),
    bodyMedium = baseline.bodyMedium.copy(fontFamily = appFontFamily),
    bodySmall = baseline.bodySmall.copy(fontFamily = appFontFamily),
    labelLarge = baseline.labelLarge.copy(fontFamily = appFontFamily),
    labelMedium = baseline.labelMedium.copy(fontFamily = appFontFamily),
    labelSmall = baseline.labelSmall.copy(fontFamily = appFontFamily),
  )
}

