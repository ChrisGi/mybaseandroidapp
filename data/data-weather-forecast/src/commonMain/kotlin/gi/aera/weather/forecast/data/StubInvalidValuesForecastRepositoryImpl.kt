package gi.aera.weather.forecast.data

import ForecastDailyResponse
import gi.aera.domain.model.ApiResponse
import gi.aera.weather.forecast.domain.model.ForecastHourlyResponse
import gi.aera.weather.forecast.domain.model.ForecastParams
import gi.aera.weather.forecast.domain.model.RealtimeWeatherResponse
import kotlinx.serialization.json.Json

class StubInvalidValuesForecastRepositoryImpl : ForecastRepository {

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
      "      \"uvHealthConcern\": 1,\n" +
      "      \"uvIndex\": 5,\n" +
      "      \"visibility\": 8.5,\n" +
      "      \"weatherCode\": 9999,\n" +
      "      \"windDirection\": 180,\n" +
      "      \"windGust\": 6.2\n" +
      "    }\n" +
      "  },\n" +
      "  \"unitSystem\": \"${params.units}\"\n" +
      "}\n"
    val response = Json.decodeFromString(RealtimeWeatherResponse.serializer(), json)
    return ApiResponse.Success(response)
  }
  override suspend fun forecastDaily(params: ForecastParams): ApiResponse<ForecastDailyResponse> {
    TODO("Not implemented in this scenario")
  }

  override suspend fun forecastHourly(params: ForecastParams): ApiResponse<ForecastHourlyResponse> {
    TODO("Not implemented in this scenario")
  }
}
