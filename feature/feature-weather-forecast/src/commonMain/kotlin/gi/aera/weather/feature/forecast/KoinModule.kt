package gi.aera.weather.feature.forecast

import gi.aera.weather.feature.forecast.domain.WeeklyForecastViewStateFactory
import gi.aera.weather.feature.forecast.domain.CurrentWeatherViewStateFactory
import gi.aera.weather.feature.forecast.presentation.ForecastViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val weatherForecastFeatureModule = module {
  factoryOf(::WeeklyForecastViewStateFactory)
  factoryOf(::CurrentWeatherViewStateFactory)
  viewModelOf(::ForecastViewModel)
}
