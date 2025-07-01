package gi.aera.prefrences

import org.koin.core.module.Module
import org.koin.dsl.module

val preferencesModule = module {
  includes(preferencesPlatformModule)
}

expect val preferencesPlatformModule: Module
