package gi.aera.location

import gi.aera.location.data.GpsLocationRepositoryImpl
import gi.aera.location.domain.repository.GpsLocationRepository
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual val platformModule: Module = module {
  factoryOf(::GpsLocationRepositoryImpl) bind GpsLocationRepository::class
}
