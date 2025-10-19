package gi.aera.weather.domain.model

sealed class UnitSystemValues(
  val temperature: String,
  val windSpeed: String,
  val rainIntensity: String,
  val pressure: String,
  val humidity: String,
  val visibility: String,
) {
  data object Metric : UnitSystemValues(
    "C",
    "m/s",
    "mm",
    "hPa",
    "%",
    "km",
  )

  data object Imperial : UnitSystemValues(
    "F",
    "mph",
    "in",
    "inHg",
    "%",
    "mi",
  )
}
