package gi.aera.weather.forecast.domain.model

import gi.aera.appsettings.domain.model.UnitSystem
import kotlinx.serialization.Serializable

@Serializable
data class RealtimeWeatherResponse(
  val data: RealtimeWeatherData,
  val unitSystem: UnitSystem = UnitSystem.METRIC,
)

@Serializable
data class RealtimeWeatherData(
  val time: String,
  val values: RealtimeWeatherValues,
)

@Serializable
data class RealtimeWeatherValues(
  val cloudBase: Double? = null,
  val cloudCeiling: Double? = null,
  val cloudCover: Double? = null,
  val dewPoint: Double? = null,
  val freezingRainIntensity: Double? = null,
  val humidity: Double = 0.0,
  val precipitationProbability: Double? = null,
  val pressureSurfaceLevel: Double = 0.0,
  val rainIntensity: Double = 0.0,
  val sleetIntensity: Double? = null,
  val snowIntensity: Double? = null,
  val temperature: Double = 10.0,
  val temperatureApparent: Double,
  val uvHealthConcern: Double? = null,
  val uvIndex: Double = 0.0,
  val visibility: Double = 0.0,
  val weatherCode: Int? = null,
  val windDirection: Double? = null,
  val windGust: Double? = null,
  val windSpeed: Double = 0.0,
)
