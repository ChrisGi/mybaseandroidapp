package gi.aera.weather.forecast.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ForecastHourlyResponse(
  val timelines: TimelinesHourly,
)

@Serializable
data class TimelinesHourly(
  val hourly: List<HourlyWeather> = emptyList(),
)

@Serializable
data class HourlyWeather(
  val time: String,
  val values: HourlyWeatherValues,
)

@Serializable
data class HourlyWeatherValues(
  val cloudBase: Double? = null,
  val cloudCeiling: Double? = null,
  val cloudCover: Long? = null,
  val dewPoint: Double? = null,
  val evapotranspiration: Double? = null,
  val freezingRainIntensity: Long? = null,
  val humidity: Long? = null,
  val iceAccumulation: Long? = null,
  val iceAccumulationLwe: Long? = null,
  val precipitationProbability: Long? = null,
  val pressureSeaLevel: Double? = null,
  val pressureSurfaceLevel: Double? = null,
  val rainAccumulation: Double? = null,
  val rainIntensity: Double? = null,
  val sleetAccumulation: Long? = null,
  val sleetAccumulationLwe: Long? = null,
  val sleetIntensity: Long? = null,
  val snowAccumulation: Long? = null,
  val snowAccumulationLwe: Long? = null,
  val snowIntensity: Long? = null,
  val temperature: Double? = null,
  val temperatureApparent: Double? = null,
  val uvHealthConcern: Long? = null,
  val uvIndex: Long? = null,
  val visibility: Double? = null,
  val weatherCode: Long? = null,
  val windDirection: Long? = null,
  val windGust: Double? = null,
  val windSpeed: Double? = null,
)
