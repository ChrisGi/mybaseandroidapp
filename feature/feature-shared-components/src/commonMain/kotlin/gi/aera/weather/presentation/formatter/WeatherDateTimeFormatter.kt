package gi.aera.weather.presentation.formatter

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.DayOfWeekNames
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime

object WeatherDateTimeFormatter {

  fun formatWeekdayShort(date: String) =
    Instant.parse(date).toLocalDateTime(TimeZone.currentSystemDefault()).date.format(
      LocalDate.Format {
        dayOfWeek(DayOfWeekNames.ENGLISH_ABBREVIATED)
      },
    )

  fun formatFullDate(date: String) =
    Instant.parse(date).toLocalDateTime(TimeZone.currentSystemDefault()).date.format(
      LocalDate.Format {
        dayOfWeek(DayOfWeekNames.ENGLISH_FULL)
        char(',')
        char(' ')
        dayOfMonth()
        monthName(MonthNames.ENGLISH_FULL)
      },
    )
}
