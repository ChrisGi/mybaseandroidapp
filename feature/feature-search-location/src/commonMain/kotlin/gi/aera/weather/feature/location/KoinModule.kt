package gi.aera.weather.feature.location

import gi.aera.weather.feature.location.domain.WeatherLocationStateFactory
import gi.aera.weather.feature.location.presentation.WeatherLocationViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val weatherLocationsFeatureModule = module {
  factory { WeatherLocationStateFactory() }
  viewModelOf(::WeatherLocationViewModel)
}
