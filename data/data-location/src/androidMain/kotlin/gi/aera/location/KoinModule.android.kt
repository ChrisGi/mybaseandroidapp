package gi.aera.location

import com.google.android.gms.location.LocationServices
import gi.aera.location.data.GpsLocationProvider
import gi.aera.location.domain.repository.GpsLocationRepository
import gi.aera.location.data.GpsLocationRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual val platformModule = module {
  single { LocationServices.getFusedLocationProviderClient(androidContext()) }
  singleOf(::GpsLocationProvider)

  factoryOf(::GpsLocationRepositoryImpl) bind GpsLocationRepository::class
}
