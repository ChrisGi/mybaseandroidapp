package gi.aera.ui.navigation

import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val navigationModule = module {
  includes(platformModule)

  singleOf(::NavigationManager)
}

expect val platformModule: Module
