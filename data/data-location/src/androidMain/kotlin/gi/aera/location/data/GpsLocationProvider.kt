package gi.aera.location.data

import android.Manifest
import androidx.annotation.RequiresPermission
import com.google.android.gms.location.FusedLocationProviderClient
import gi.aera.location.domain.model.GpsCoordinates
import gi.aera.location.domain.model.LocationNotFoundException
import gi.aera.location.domain.model.PermissionException
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class GpsLocationProvider(private val fusedLocationClient: FusedLocationProviderClient) {

  @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
  fun getLastGpsLocation(): Flow<GpsCoordinates> = callbackFlow {
    try {
      fusedLocationClient.lastLocation
        .addOnSuccessListener { location ->
          if (location == null) {
            close(LocationNotFoundException())
          } else {
            trySend(GpsCoordinates(location.latitude, location.longitude))
            close()
          }
        }
        .addOnFailureListener { exception ->
          close(exception)
        }
    } catch (_: SecurityException) {
      close(PermissionException())
    }

    awaitClose()
  }
}
