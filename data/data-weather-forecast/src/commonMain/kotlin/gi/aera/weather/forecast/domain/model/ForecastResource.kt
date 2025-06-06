package gi.aera.weather.forecast.domain.model

import io.ktor.resources.Resource

@Resource("/weather")
class Weather {
  @Resource("forecast")
  class Forecast(
    val parent: Weather = Weather(),
    val location: String,
    val timesteps: String? = "1h",
    val units: String? = "metric"
  )
}