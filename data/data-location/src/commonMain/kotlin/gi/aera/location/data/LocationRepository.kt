package gi.aera.location.data

internal interface LocationRepository {
  suspend fun getLastLocation(): Pair<Double, Double>
}
