package gi.aera.shared.di

import gi.aera.location.locationModule
import gi.aera.network.di.networkModule
import gi.aera.weather.feature.forecast.weatherForecastFeatureModule
import gi.aera.weather.forecast.weatherForecastDataModule

val appModules = listOf(
  networkModule,
  locationModule,

  weatherForecastDataModule,
  weatherForecastFeatureModule,
)
