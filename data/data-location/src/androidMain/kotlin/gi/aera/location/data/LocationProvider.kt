package gi.aera.location.data

import android.Manifest
import androidx.annotation.RequiresPermission
import com.google.android.gms.location.FusedLocationProviderClient
import gi.aera.location.domain.model.LocationNotFoundException
import kotlinx.coroutines.tasks.await

class LocationProvider(private val fusedLocationClient: FusedLocationProviderClient) {

  @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
  suspend fun getLastLocation() = fusedLocationClient.lastLocation.await().let {
    if (it == null) {
      throw LocationNotFoundException()
    } else {
      Pair(it.latitude, it.longitude)
    }
  }
}
