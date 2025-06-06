import kotlinx.serialization.Serializable

@Serializable
data class ForecastResponseDaily(
  val timelines: TimelinesDaily,
)

@Serializable
data class ForecastResponseHourly(
  val timelines: TimelinesHourly,
)

@Serializable
data class TimelinesDaily(
  val daily: List<DailyWeather> = emptyList(),
)

@Serializable
data class TimelinesHourly(
  val hourly: List<HourlyWeather> = emptyList()
)

@Serializable
data class DailyWeather(
  val time: String,
  val values: DailyWeatherValues
)

@Serializable
data class DailyWeatherValues(
  val cloudBaseAvg: Double? = null,
  val cloudBaseMax: Double? = null,
  val cloudBaseMin: Double? = null,
  val cloudCeilingAvg: Double? = null,
  val cloudCeilingMax: Double? = null,
  val cloudCeilingMin: Double? = null,
  val cloudCoverAvg: Int? = null,
  val cloudCoverMax: Int? = null,
  val cloudCoverMin: Int? = null,
  val dewPointAvg: Double? = null,
  val dewPointMax: Double? = null,
  val dewPointMin: Double? = null,
  val evapotranspirationAvg: Double? = null,
  val evapotranspirationMax: Double? = null,
  val evapotranspirationMin: Double? = null,
  val evapotranspirationSum: Double? = null,
  val freezingRainIntensityAvg: Double? = null,
  val freezingRainIntensityMax: Double? = null,
  val freezingRainIntensityMin: Double? = null,
  val humidityAvg: Int? = null,
  val humidityMax: Int? = null,
  val humidityMin: Int? = null,
  val iceAccumulationAvg: Double? = null,
  val iceAccumulationLweAvg: Double? = null,
  val iceAccumulationLweMax: Double? = null,
  val iceAccumulationLweMin: Double? = null,
  val iceAccumulationLweSum: Double? = null,
  val iceAccumulationMax: Double? = null,
  val iceAccumulationMin: Double? = null,
  val iceAccumulationSum: Double? = null,
  val moonriseTime: String? = null,
  val moonsetTime: String? = null,
  val precipitationProbabilityAvg: Double? = null,
  val precipitationProbabilityMax: Double? = null,
  val precipitationProbabilityMin: Double? = null,
  val pressureSeaLevelAvg: Double? = null,
  val pressureSeaLevelMax: Double? = null,
  val pressureSeaLevelMin: Double? = null,
  val pressureSurfaceLevelAvg: Double? = null,
  val pressureSurfaceLevelMax: Double? = null,
  val pressureSurfaceLevelMin: Double? = null,
  val rainAccumulationAvg: Double? = null,
  val rainAccumulationMax: Double? = null,
  val rainAccumulationMin: Double? = null,
  val rainAccumulationSum: Double? = null,
  val rainIntensityAvg: Double? = null,
  val rainIntensityMax: Double? = null,
  val rainIntensityMin: Double? = null,
  val sleetAccumulationAvg: Double? = null,
  val sleetAccumulationLweAvg: Double? = null,
  val sleetAccumulationLweMax: Double? = null,
  val sleetAccumulationLweMin: Double? = null,
  val sleetAccumulationLweSum: Double? = null,
  val sleetAccumulationMax: Double? = null,
  val sleetAccumulationMin: Double? = null,
  val sleetIntensityAvg: Double? = null,
  val sleetIntensityMax: Double? = null,
  val sleetIntensityMin: Double? = null,
  val snowAccumulationAvg: Double? = null,
  val snowAccumulationLweAvg: Double? = null,
  val snowAccumulationLweMax: Double? = null,
  val snowAccumulationLweMin: Double? = null,
  val snowAccumulationLweSum: Double? = null,
  val snowAccumulationMax: Double? = null,
  val snowAccumulationMin: Double? = null,
  val snowAccumulationSum: Double? = null,
  val snowIntensityAvg: Double? = null,
  val snowIntensityMax: Double? = null,
  val snowIntensityMin: Double? = null,
  val sunriseTime: String? = null,
  val sunsetTime: String? = null,
  val temperatureApparentAvg: Double? = null,
  val temperatureApparentMax: Double? = null,
  val temperatureApparentMin: Double? = null,
  val temperatureAvg: Double? = null,
  val temperatureMax: Double? = null,
  val temperatureMin: Double? = null,
  val temperature: Double? = null, // nullable because it's null in second day
  val uvHealthConcernAvg: Int? = null,
  val uvHealthConcernMax: Int? = null,
  val uvHealthConcernMin: Int? = null,
  val uvIndexAvg: Int? = null,
  val uvIndexMax: Int? = null,
  val uvIndexMin: Int? = null,
  val visibilityAvg: Double? = null,
  val visibilityMax: Double? = null,
  val visibilityMin: Double? = null,
  val weatherCodeMax: Int? = null,
  val weatherCodeMin: Int? = null,
  val windDirectionAvg: Double? = null,
  val windGustAvg: Double? = null,
  val windGustMax: Double? = null,
  val windGustMin: Double? = null,
  val windSpeedAvg: Double? = null,
  val windSpeedMax: Double? = null,
  val windSpeedMin: Double? = null
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
