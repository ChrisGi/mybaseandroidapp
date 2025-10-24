package gi.aera.appsettings

import gi.aera.appsettings.data.UnitSettingsRepositoryImpl
import gi.aera.appsettings.domain.appSettingsDomainModule
import gi.aera.appsettings.domain.repository.UnitSettingsRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appSettingsDataModule = module {
  singleOf(::UnitSettingsRepositoryImpl) bind UnitSettingsRepository::class

  includes(appSettingsDomainModule)
}
