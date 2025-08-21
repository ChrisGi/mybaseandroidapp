package gi.aera.location.data

import android.Manifest
import androidx.annotation.RequiresPermission
import com.google.android.gms.location.FusedLocationProviderClient
import gi.aera.location.domain.model.LocationNotFoundException
import gi.aera.location.domain.model.PermissionException
import kotlinx.coroutines.tasks.await

class LocationProvider(private val fusedLocationClient: FusedLocationProviderClient) {

  @Suppress("SwallowedException")
  @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
  suspend fun getLastLocation() = try {
    fusedLocationClient.lastLocation.await().let {
      if (it == null) {
        throw LocationNotFoundException()
      } else {
        Pair(it.latitude, it.longitude)
      }
    }
  } catch (e: SecurityException) {
    throw PermissionException()
  }
}
