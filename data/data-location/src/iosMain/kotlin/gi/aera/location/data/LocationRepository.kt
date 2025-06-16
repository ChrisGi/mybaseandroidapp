package gi.aera.location.data

actual class LocationRepository : Location {
  override suspend fun getCurrentLocation(): Pair<Double, Double> {
    TODO("Not yet implemented")
  }
}
