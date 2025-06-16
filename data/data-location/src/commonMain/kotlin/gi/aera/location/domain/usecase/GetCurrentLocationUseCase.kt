package gi.aera.location.domain.usecase

import gi.aera.location.data.Location
import gi.aera.location.domain.model.LocationNotFoundException
import gi.aera.location.domain.model.LocationSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class GetCurrentLocationUseCase(
  private val locationRepository: Location,
  private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) {

  @Suppress("SwallowedException")
  suspend operator fun invoke() = withContext(dispatcher) {
    val (latitude, longitude) = try {
      locationRepository.getCurrentLocation()
    } catch (e: LocationNotFoundException) {
      return@withContext LocationSource.City(DEFAULT_LOCATION)
    }
    return@withContext LocationSource.GpsCoordinates(latitude, longitude)
  }

  companion object {
    private const val DEFAULT_LOCATION = "London"
  }
}
