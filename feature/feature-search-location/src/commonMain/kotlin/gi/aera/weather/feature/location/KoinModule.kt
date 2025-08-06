package gi.aera.weather.feature.location

import dev.icerock.moko.permissions.PermissionsController
import gi.aera.weather.feature.location.domain.WeatherLocationFactory
import gi.aera.weather.feature.location.presentation.WeatherLocationViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val weatherLocationsFeatureModule = module {
  factory { WeatherLocationFactory() }
  viewModel { (params: PermissionsController) ->
    WeatherLocationViewModel(params, get(), get(), get(), get(), get(), get())
  }
}
