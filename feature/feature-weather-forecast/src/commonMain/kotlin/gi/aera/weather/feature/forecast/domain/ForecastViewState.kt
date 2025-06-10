package gi.aera.weather.feature.forecast.domain

import kotlinx.datetime.LocalDate
import kotlinx.datetime.format
import kotlinx.datetime.format.DayOfWeekNames
import org.jetbrains.compose.resources.StringResource

data class ForecastViewState(
  val currentTemperature: String,
  val condition: StringResource,
  val conditionIcon: String,
  val localDate: LocalDate,
) {

  val forecastDay: String
    get() = localDate.format(
      LocalDate.Format {
        dayOfWeek(DayOfWeekNames.ENGLISH_ABBREVIATED)
      },
    ).lowercase()
}
