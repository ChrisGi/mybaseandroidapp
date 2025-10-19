package gi.aera.ui.navigation

import gi.aera.ui.navigation.domain.model.NavigationManager
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val navigationModule = module {
  includes(platformModule)

  singleOf(::NavigationManagerImpl) bind NavigationManager::class
}

expect val platformModule: Module
