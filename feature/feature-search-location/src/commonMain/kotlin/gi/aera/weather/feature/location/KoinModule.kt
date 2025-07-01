package gi.aera.weather.feature.location

import gi.aera.weather.feature.location.presentation.SearchLocationViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val searchLocationModule = module {
  viewModelOf(::SearchLocationViewModel)
}
