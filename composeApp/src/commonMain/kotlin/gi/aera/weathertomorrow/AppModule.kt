package gi.aera.weathertomorrow

import gi.aera.appsettings.appSettingsDataModule
import gi.aera.common.dispatchers.dispatchersKoinModule
import gi.aera.feature.settings.appSettingsFeatureModule
import gi.aera.location.locationModule
import gi.aera.network.networkModule
import gi.aera.prefrences.preferencesModule
import gi.aera.ui.navigation.navigationModule
import gi.aera.weather.feature.forecast.weatherForecastFeatureModule
import gi.aera.weather.feature.location.weatherLocationsFeatureModule
import gi.aera.weather.feature.search.searchLocationFeatureModule
import gi.aera.weather.forecast.weatherForecastDataModule

val appModules = listOf(
  networkModule,
  locationModule,
  preferencesModule,
  navigationModule,
  dispatchersKoinModule,

  weatherForecastDataModule,
  appSettingsDataModule,

  weatherForecastFeatureModule,
  weatherLocationsFeatureModule,
  searchLocationFeatureModule,
  appSettingsFeatureModule,
)
