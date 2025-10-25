package gi.aera.weather.forecast.source

import io.ktor.resources.Resource

@Resource("/weather")
class RealtimeWeatherResource {
  @Resource("/realtime")
  class Realtime(
    val parent: RealtimeWeatherResource = RealtimeWeatherResource(),
    val location: String,
    val units: String,
  )
}
