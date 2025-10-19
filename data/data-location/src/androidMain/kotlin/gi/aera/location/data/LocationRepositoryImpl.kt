package gi.aera.location.data

import android.Manifest
import androidx.annotation.RequiresPermission

internal actual class LocationRepositoryImpl(private val locationProvider: GpsLocationProvider) : LocationRepository {

  @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
  override fun getLastGpsLocation() = locationProvider.getLastGpsLocation()
}
