package gi.aera.weather.forecast

import gi.aera.weather.forecast.data.ForecastApi
import gi.aera.weather.forecast.data.ForecastRepository
import gi.aera.weather.forecast.domain.usecase.GetDailyForecastUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val weatherForecastDataModule = module {
  singleOf(::ForecastApi)
  singleOf(::ForecastRepository)
  single { GetDailyForecastUseCase(get()) }
}
