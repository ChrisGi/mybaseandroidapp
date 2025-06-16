package gi.aera.weather.feature.forecast.domain

import gi.aera.ui.text.UiString
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format
import kotlinx.datetime.format.DayOfWeekNames

data class ForecastViewState(
  val currentTemperature: String,
  val condition: UiString,
  val conditionIcon: String,
  val localDate: LocalDate,
  val location: UiString,
) {

  val forecastDay: String
    get() = localDate.format(
      LocalDate.Format {
        dayOfWeek(DayOfWeekNames.ENGLISH_ABBREVIATED)
      },
    ).lowercase()
}
