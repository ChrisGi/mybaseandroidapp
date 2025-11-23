package gi.aera.location.data

import gi.aera.location.domain.model.GpsCoordinates
import gi.aera.location.domain.model.LocationNotFoundException
import gi.aera.location.domain.model.PermissionException
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import platform.CoreLocation.CLLocation
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.CLLocationManagerDelegateProtocol
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedAlways
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedWhenInUse
import platform.CoreLocation.kCLAuthorizationStatusDenied
import platform.CoreLocation.kCLAuthorizationStatusNotDetermined
import platform.CoreLocation.kCLAuthorizationStatusRestricted
import platform.CoreLocation.kCLErrorDenied
import platform.CoreLocation.kCLErrorLocationUnknown
import platform.CoreLocation.kCLLocationAccuracyBest
import platform.Foundation.NSError
import platform.darwin.NSObject

@OptIn(ExperimentalForeignApi::class)
internal class GpsLocationProvider {

  private val locationManager = CLLocationManager()

  fun getLastGpsLocation(): Flow<GpsCoordinates> = callbackFlow {
    // Create a delegate to handle location updates
    val delegate = object : NSObject(), CLLocationManagerDelegateProtocol {

      override fun locationManager(
        manager: CLLocationManager,
        didUpdateLocations: List<*>,
      ) {
        // Get the most recent location
        val location = didUpdateLocations.lastOrNull() as? CLLocation

        if (location != null) {
          // Send coordinates
          val coords = location.coordinate.useContents {
            GpsCoordinates(
              latitude = this.latitude,
              longitude = this.longitude,
            )
          }
          trySend(coords)
          // Stop updates and close the flow
          manager.stopUpdatingLocation()
          close()
        } else {
          close(LocationNotFoundException())
        }
      }

      override fun locationManager(
        manager: CLLocationManager,
        didFailWithError: NSError,
      ) {
        // Handle errors
        when (didFailWithError.code) {
          kCLErrorDenied -> {
            close(PermissionException())
          }

          kCLErrorLocationUnknown -> {
            close(LocationNotFoundException())
          }

          else -> {
            close(Exception("Location error: ${didFailWithError.localizedDescription}"))
          }
        }
        manager.stopUpdatingLocation()
      }

      override fun locationManagerDidChangeAuthorization(manager: CLLocationManager) {
        // This is called when authorization status changes (e.g., user responds to permission dialog)
        val status = manager.authorizationStatus
        when (status) {
          kCLAuthorizationStatusAuthorizedWhenInUse,
          kCLAuthorizationStatusAuthorizedAlways,
            -> {
            // Permission granted - request location
            manager.requestLocation()
          }

          kCLAuthorizationStatusDenied,
          kCLAuthorizationStatusRestricted,
            -> {
            // Permission denied
            close(PermissionException())
          }

          kCLAuthorizationStatusNotDetermined -> {
            // Still waiting for user decision, do nothing
          }
        }
      }
    }

    // Set the delegate
    locationManager.delegate = delegate

    // Set desired accuracy for better results
    locationManager.desiredAccuracy = kCLLocationAccuracyBest

    // Check authorization status
    val authStatus = locationManager.authorizationStatus
    when (authStatus) {
      kCLAuthorizationStatusNotDetermined -> {
        // Request permission - locationManagerDidChangeAuthorization will be called when user responds
        locationManager.requestWhenInUseAuthorization()
      }

      kCLAuthorizationStatusDenied,
      kCLAuthorizationStatusRestricted,
        -> {
        close(PermissionException())
        return@callbackFlow
      }

      kCLAuthorizationStatusAuthorizedWhenInUse,
      kCLAuthorizationStatusAuthorizedAlways,
        -> {
        // Permission already granted - request location immediately
        locationManager.requestLocation()
      }
    }

    // Clean up when flow is cancelled
    awaitClose {
      locationManager.stopUpdatingLocation()
      locationManager.delegate = null
    }
  }
}
