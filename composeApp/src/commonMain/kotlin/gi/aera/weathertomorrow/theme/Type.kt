package gi.aera.weathertomorrow.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import gi.aera.weather.AlanSans_Black
import gi.aera.weather.AlanSans_Bold
import gi.aera.weather.AlanSans_ExtraBold
import gi.aera.weather.AlanSans_Light
import gi.aera.weather.AlanSans_Medium
import gi.aera.weather.AlanSans_Regular
import gi.aera.weather.AlanSans_SemiBold
import gi.aera.weather.Res
import org.jetbrains.compose.resources.Font

@Composable
fun AlanSansFamily() = FontFamily(
  Font(Res.font.AlanSans_Light, weight = FontWeight.Light),
  Font(Res.font.AlanSans_Regular, weight = FontWeight.Normal),
  Font(Res.font.AlanSans_Medium, weight = FontWeight.Medium),
  Font(Res.font.AlanSans_SemiBold, weight = FontWeight.SemiBold),
  Font(Res.font.AlanSans_Bold, weight = FontWeight.Bold),
  Font(Res.font.AlanSans_ExtraBold, weight = FontWeight.ExtraBold),
  Font(Res.font.AlanSans_Black, weight = FontWeight.Black),
)

@Composable
fun appTypography(): Typography = Typography().run {
  val fontFamily = AlanSansFamily()
  copy(
    displayLarge = displayLarge.copy(fontFamily = fontFamily),
    displayMedium = displayMedium.copy(fontFamily = fontFamily),
    displaySmall = displaySmall.copy(fontFamily = fontFamily),
    headlineLarge = headlineLarge.copy(fontFamily = fontFamily),
    headlineMedium = headlineMedium.copy(fontFamily = fontFamily),
    headlineSmall = headlineSmall.copy(fontFamily = fontFamily),
    titleLarge = titleLarge.copy(fontFamily = fontFamily),
    titleMedium = titleMedium.copy(fontFamily = fontFamily),
    titleSmall = titleSmall.copy(fontFamily = fontFamily),
    bodyLarge = bodyLarge.copy(fontFamily = fontFamily),
    bodyMedium = bodyMedium.copy(fontFamily = fontFamily),
    bodySmall = bodySmall.copy(fontFamily = fontFamily),
    labelLarge = labelLarge.copy(fontFamily = fontFamily),
    labelMedium = labelMedium.copy(fontFamily = fontFamily),
    labelSmall = labelSmall.copy(fontFamily = fontFamily),
  )
}
