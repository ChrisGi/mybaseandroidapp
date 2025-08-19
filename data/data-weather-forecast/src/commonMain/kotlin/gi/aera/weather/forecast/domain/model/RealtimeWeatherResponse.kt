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
  val cloudCover: Long? = null,
  val dewPoint: Double? = null,
  val freezingRainIntensity: Long? = null,
  val humidity: Long? = null,
  val precipitationProbability: Long? = null,
  val pressureSurfaceLevel: Double? = null,
  val rainIntensity: Long? = null,
  val sleetIntensity: Long? = null,
  val snowIntensity: Long? = null,
  val temperature: Double? = null,
  val temperatureApparent: Double? = null,
  val uvHealthConcern: Long? = null,
  val uvIndex: Long? = null,
  val visibility: Double? = null,
  val weatherCode: Int? = null,
  val windDirection: Long? = null,
  val windGust: Double? = null,
  val windSpeed: Double? = null,
)
