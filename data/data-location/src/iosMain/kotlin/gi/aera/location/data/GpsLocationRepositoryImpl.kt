package gi.aera.location.data

import gi.aera.location.domain.repository.GpsLocationRepository

internal actual class GpsLocationRepositoryImpl(private val gpsLocationProvider: GpsLocationProvider) : GpsLocationRepository {

  actual override fun getLastGpsLocation() = gpsLocationProvider.getLastGpsLocation()
}
