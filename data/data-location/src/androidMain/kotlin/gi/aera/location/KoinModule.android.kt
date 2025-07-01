package gi.aera.location

import com.google.android.gms.location.LocationServices
import gi.aera.location.data.LocationRepository
import gi.aera.location.data.LocationProvider
import gi.aera.location.data.LocationRepositoryImpl
import gi.aera.location.domain.usecase.GetLastLocationUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val platformModule = module {
  single { LocationServices.getFusedLocationProviderClient(androidContext()) }
  singleOf(::LocationProvider)
  factoryOf(::LocationRepositoryImpl) { bind<LocationRepository>() }
  factory { GetLastLocationUseCase(get()) }
}
