package gi.aera.location.domain.model

sealed class LocationResult {
  data class Success(val location: SearchLocation) : LocationResult()
  data object PermissionRequired : LocationResult()
  data object NotFound : LocationResult()
}
