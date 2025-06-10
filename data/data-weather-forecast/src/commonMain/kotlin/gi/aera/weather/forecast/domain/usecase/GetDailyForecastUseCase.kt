package gi.aera.weather.forecast.domain.usecase

import gi.aera.weather.forecast.data.ForecastRepository
import gi.aera.weather.forecast.domain.model.ForecastParams
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class GetDailyForecastUseCase internal constructor(
  private val forecastRepository: ForecastRepository,
  private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) {

  suspend operator fun invoke(params: ForecastParams) = withContext(dispatcher) {
    forecastRepository.forecastDaily(params)
  }
}
