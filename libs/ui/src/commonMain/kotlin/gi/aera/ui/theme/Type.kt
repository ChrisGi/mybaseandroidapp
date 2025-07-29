package gi.aera.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.Font
import weathertomorrow.ui.generated.resources.Afacad_Bold
import weathertomorrow.ui.generated.resources.Afacad_Medium
import weathertomorrow.ui.generated.resources.Afacad_Regular
import weathertomorrow.ui.generated.resources.Afacad_SemiBold
import weathertomorrow.ui.generated.resources.Res

@Composable
fun AppFontFamily() = FontFamily(
  Font(Res.font.Afacad_Regular, weight = FontWeight.Normal),
  Font(Res.font.Afacad_Medium, weight = FontWeight.Medium),
  Font(Res.font.Afacad_SemiBold, weight = FontWeight.SemiBold),
  Font(Res.font.Afacad_Bold, weight = FontWeight.Bold),
)

val baseline = Typography()

@Composable
fun appTypography(): Typography {
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
