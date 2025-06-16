package gi.aera.location.domain.model

sealed class LocationSource {

  data class GpsCoordinates(val latitude: Double, val longitude: Double) : LocationSource() {
    override fun toString(): String {
      return "$latitude, $longitude"
    }
  }

  data class City(val name: String) : LocationSource() {
    override fun toString(): String {
      return name
    }
  }
}
