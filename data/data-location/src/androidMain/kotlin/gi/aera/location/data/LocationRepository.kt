package gi.aera.location.data

import android.Manifest
import androidx.annotation.RequiresPermission

actual class LocationRepository(private val locationProvider: LocationProvider) : Location {

  @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
  override suspend fun getCurrentLocation() = locationProvider.getLastLocation()
}
