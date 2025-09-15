package gi.aera.weather.error

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val errorNavigationModule = module {
  singleOf(::ErrorScreenNavigationManager)
}
