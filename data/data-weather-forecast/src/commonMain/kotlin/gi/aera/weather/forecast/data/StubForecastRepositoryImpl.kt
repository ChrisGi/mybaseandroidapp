package gi.aera.weather.forecast.data

import ForecastDailyResponse
import gi.aera.common.model.ApiResponse
import gi.aera.weather.forecast.domain.model.ForecastHourlyResponse
import gi.aera.weather.forecast.domain.model.ForecastParams
import gi.aera.weather.forecast.domain.model.RealtimeWeatherResponse
import gi.aera.weather.forecast.domain.repository.ForecastRepository
import kotlinx.serialization.json.Json

class StubForecastRepositoryImpl : ForecastRepository {

  override suspend fun realtimeWeather(params: ForecastParams): ApiResponse<RealtimeWeatherResponse> {
    val json = "{\n" +
      "  \"data\": {\n" +
      "    \"time\": \"2025-08-18T10:00:00Z\",\n" +
      "    \"values\": {\n" +
      "      \"cloudBase\": 1.2,\n" +
      "      \"cloudCeiling\": 2.5,\n" +
      "      \"cloudCover\": 75,\n" +
      "      \"dewPoint\": 18.5,\n" +
      "      \"freezingRainIntensity\": 0,\n" +
      "      \"humidity\": 82,\n" +
      "      \"precipitationProbability\": 60,\n" +
      "      \"pressureSurfaceLevel\": 1012.3,\n" +
      "      \"rainIntensity\": 2,\n" +
      "      \"sleetIntensity\": 0,\n" +
      "      \"snowIntensity\": 0,\n" +
      "      \"temperature\": 22.4,\n" +
      "      \"temperatureApparent\": 23.0,\n" +
      "      \"uvHealthConcern\": 1,\n" +
      "      \"uvIndex\": 5,\n" +
      "      \"visibility\": 8.5,\n" +
      "      \"weatherCode\": 1101,\n" +
      "      \"windDirection\": 180,\n" +
      "      \"windGust\": 6.2,\n" +
      "      \"windSpeed\": 3.5\n" +
      "    }\n" +
      "  },\n" +
      "  \"unitSystem\": \"${params.units}\"\n" +
      "}\n"
    val response = Json.decodeFromString(RealtimeWeatherResponse.serializer(), json)
    return ApiResponse.Success(response)
  }

  @Suppress("LongMethod")
  override suspend fun forecastDaily(params: ForecastParams): ApiResponse<ForecastDailyResponse> {
    val json = "{\n" +
      "  \"timelines\": {\n" +
      "    \"daily\": [\n" +
      "      {\n" +
      "        \"time\": \"2025-08-18T00:00:00Z\",\n" +
      "        \"values\": {\n" +
      "          \"cloudBaseAvg\": 1.5,\n" +
      "          \"cloudBaseMax\": 2.3,\n" +
      "          \"cloudBaseMin\": 0.8,\n" +
      "          \"cloudCeilingAvg\": 3.0,\n" +
      "          \"cloudCeilingMax\": 3.8,\n" +
      "          \"cloudCeilingMin\": 2.5,\n" +
      "          \"cloudCoverAvg\": 65,\n" +
      "          \"cloudCoverMax\": 90,\n" +
      "          \"cloudCoverMin\": 40,\n" +
      "          \"dewPointAvg\": 18.2,\n" +
      "          \"dewPointMax\": 20.1,\n" +
      "          \"dewPointMin\": 16.5,\n" +
      "          \"humidityAvg\": 75,\n" +
      "          \"humidityMax\": 85,\n" +
      "          \"humidityMin\": 60,\n" +
      "          \"precipitationProbabilityAvg\": 30,\n" +
      "          \"precipitationProbabilityMax\": 50,\n" +
      "          \"precipitationProbabilityMin\": 10,\n" +
      "          \"pressureSeaLevelAvg\": 1012.5,\n" +
      "          \"pressureSeaLevelMax\": 1015.2,\n" +
      "          \"pressureSeaLevelMin\": 1010.1,\n" +
      "          \"rainAccumulationSum\": 2.5,\n" +
      "          \"rainIntensityAvg\": 0.2,\n" +
      "          \"rainIntensityMax\": 1.0,\n" +
      "          \"rainIntensityMin\": 0,\n" +
      "          \"snowAccumulationSum\": 0,\n" +
      "          \"snowIntensityAvg\": 0,\n" +
      "          \"sunriseTime\": \"2025-08-18T05:30:00Z\",\n" +
      "          \"sunsetTime\": \"2025-08-18T19:45:00Z\",\n" +
      "          \"temperatureApparentAvg\": 23.1,\n" +
      "          \"temperatureApparentMax\": 28.0,\n" +
      "          \"temperatureApparentMin\": 19.5,\n" +
      "          \"temperatureAvg\": 22.5,\n" +
      "          \"temperatureMax\": 27.0,\n" +
      "          \"temperatureMin\": 18.0,\n" +
      "          \"temperature\": 22.5,\n" +
      "          \"uvIndexMax\": 7,\n" +
      "          \"uvIndexMin\": 1,\n" +
      "          \"visibilityAvg\": 9.5,\n" +
      "          \"weatherCodeMax\": 2100,\n" +
      "          \"weatherCodeMin\": 1000,\n" +
      "          \"windDirectionAvg\": 190,\n" +
      "          \"windGustMax\": 8.2,\n" +
      "          \"windSpeedAvg\": 4.5\n" +
      "        }\n" +
      "      },\n" +
      "      {\n" +
      "        \"time\": \"2025-08-19T00:00:00Z\",\n" +
      "        \"values\": {\n" +
      "          \"cloudBaseAvg\": 2.0,\n" +
      "          \"cloudBaseMax\": 3.1,\n" +
      "          \"cloudBaseMin\": 1.2,\n" +
      "          \"cloudCeilingAvg\": 4.2,\n" +
      "          \"cloudCeilingMax\": 5.0,\n" +
      "          \"cloudCeilingMin\": 3.5,\n" +
      "          \"cloudCoverAvg\": 80,\n" +
      "          \"cloudCoverMax\": 95,\n" +
      "          \"cloudCoverMin\": 60,\n" +
      "          \"dewPointAvg\": 17.5,\n" +
      "          \"dewPointMax\": 19.0,\n" +
      "          \"dewPointMin\": 16.0,\n" +
      "          \"humidityAvg\": 70,\n" +
      "          \"humidityMax\": 82,\n" +
      "          \"humidityMin\": 55,\n" +
      "          \"precipitationProbabilityAvg\": 40,\n" +
      "          \"precipitationProbabilityMax\": 70,\n" +
      "          \"precipitationProbabilityMin\": 20,\n" +
      "          \"pressureSeaLevelAvg\": 1010.8,\n" +
      "          \"pressureSeaLevelMax\": 1013.0,\n" +
      "          \"pressureSeaLevelMin\": 1009.2,\n" +
      "          \"rainAccumulationSum\": 5.8,\n" +
      "          \"rainIntensityAvg\": 0.5,\n" +
      "          \"rainIntensityMax\": 2.0,\n" +
      "          \"rainIntensityMin\": 0,\n" +
      "          \"snowAccumulationSum\": 0,\n" +
      "          \"snowIntensityAvg\": 0,\n" +
      "          \"sunriseTime\": \"2025-08-19T05:31:00Z\",\n" +
      "          \"sunsetTime\": \"2025-08-19T19:44:00Z\",\n" +
      "          \"temperatureApparentAvg\": 21.4,\n" +
      "          \"temperatureApparentMax\": 25.5,\n" +
      "          \"temperatureApparentMin\": 17.2,\n" +
      "          \"temperatureAvg\": 20.9,\n" +
      "          \"temperatureMax\": 25.0,\n" +
      "          \"temperatureMin\": 17.0,\n" +
      "          \"temperature\": null,\n" +
      "          \"uvIndexMax\": 6,\n" +
      "          \"uvIndexMin\": 0,\n" +
      "          \"visibilityAvg\": 8.2,\n" +
      "          \"weatherCodeMax\": 5122,\n" +
      "          \"weatherCodeMin\": 4201,\n" +
      "          \"windDirectionAvg\": 170,\n" +
      "          \"windGustMax\": 10.5,\n" +
      "          \"windSpeedAvg\": 5.2\n" +
      "        }\n" +
      "      }\n" +
      "    ]\n" +
      "  },\n" +
      "  \"unitSystem\": \"${params.units}\"\n" +
      "}\n"
    val response = Json.decodeFromString(ForecastDailyResponse.serializer(), json)
    return ApiResponse.Success(response)
  }

  override suspend fun forecastHourly(params: ForecastParams): ApiResponse<ForecastHourlyResponse> {
    TODO("Not yet implemented")
  }
}
