package gi.aera.location.data

interface Location {

  suspend fun getCurrentLocation(): Pair<Double, Double>
}
