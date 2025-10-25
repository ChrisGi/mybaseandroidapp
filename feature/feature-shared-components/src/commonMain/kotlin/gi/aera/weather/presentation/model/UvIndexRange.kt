package gi.aera.weather.presentation.model

import gi.aera.weather.Res
import gi.aera.weather.uv_index_extreme
import gi.aera.weather.uv_index_high
import gi.aera.weather.uv_index_low
import gi.aera.weather.uv_index_moderate
import gi.aera.weather.uv_index_unknown
import gi.aera.weather.uv_index_very_high
import org.jetbrains.compose.resources.StringResource

@Suppress("MagicNumber")
internal enum class UvIndexRange(
  val range: IntRange,
  val description: StringResource,
) {
  LOW(0..2, Res.string.uv_index_low),
  MODERATE(3..5, Res.string.uv_index_moderate),
  HIGH(6..7, Res.string.uv_index_high),
  VERY_HIGH(8..10, Res.string.uv_index_very_high),
  EXTREME(11..Int.MAX_VALUE, Res.string.uv_index_extreme),
  ;

  companion object {
    fun from(uvIndex: Int): StringResource {
      return entries.find { it.range.contains(uvIndex) }?.description ?: Res.string.uv_index_unknown
    }
  }
}
