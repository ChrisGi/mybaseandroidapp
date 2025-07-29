package gi.aera.weather.feature.search

import gi.aera.weather.feature.search.presentation.SearchLocationViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val searchLocationFeatureModule = module {
  viewModelOf(::SearchLocationViewModel)
}
