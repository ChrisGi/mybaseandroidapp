package gi.aera.location.data

import android.Manifest
import androidx.annotation.RequiresPermission
import gi.aera.location.domain.model.GpsLocationRepository

internal actual class GpsLocationRepositoryImpl(private val locationProvider: GpsLocationProvider) : GpsLocationRepository {

  @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
  actual override fun getLastGpsLocation() = locationProvider.getLastGpsLocation()
}
