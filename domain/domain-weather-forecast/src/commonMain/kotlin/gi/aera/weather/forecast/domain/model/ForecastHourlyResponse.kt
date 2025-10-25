package gi.aera.weather.forecast.domain.model

import gi.aera.appsettings.domain.model.UnitSystem
import kotlinx.serialization.Serializable

@Serializable
data class ForecastHourlyResponse(
  val timelines: TimelinesHourly,
  val unitSystem: UnitSystem = UnitSystem.METRIC,
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
  val cloudCover: Double? = null,
  val dewPoint: Double? = null,
  val evapotranspiration: Double? = null,
  val freezingRainIntensity: Double? = null,
  val humidity: Double? = null,
  val iceAccumulation: Double? = null,
  val iceAccumulationLwe: Double? = null,
  val precipitationProbability: Double? = null,
  val pressureSeaLevel: Double? = null,
  val pressureSurfaceLevel: Double? = null,
  val rainAccumulation: Double? = null,
  val rainIntensity: Double? = null,
  val sleetAccumulation: Double? = null,
  val sleetAccumulationLwe: Double? = null,
  val sleetIntensity: Double? = null,
  val snowAccumulation: Double? = null,
  val snowAccumulationLwe: Double? = null,
  val snowIntensity: Double? = null,
  val temperature: Double? = null,
  val temperatureApparent: Double? = null,
  val uvHealthConcern: Double? = null,
  val uvIndex: Double? = null,
  val visibility: Double? = null,
  val weatherCode: Int? = null,
  val windDirection: Double? = null,
  val windGust: Double? = null,
  val windSpeed: Double? = null,
)
