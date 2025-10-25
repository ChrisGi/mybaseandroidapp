package gi.aera.weather.forecast.source

import io.ktor.resources.Resource

@Resource("/weather")
class ForecastResource {
  @Resource("forecast")
  class Forecast(
    val parent: ForecastResource = ForecastResource(),
    val location: String,
    val timesteps: String? = "1h",
    val units: String,
  )
}
