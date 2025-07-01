package gi.aera.location.domain.usecase

import gi.aera.location.data.LocationRepository
import gi.aera.location.domain.model.LocationNotFoundException
import gi.aera.location.domain.model.SearchLocation
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class GetLastLocationUseCase internal constructor(
  private val locationRepository: LocationRepository,
  private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) {

  suspend operator fun invoke(): SearchLocation = obtainLocation()

  @Suppress("SwallowedException")
  private suspend fun obtainLocation() = withContext(dispatcher) {
    val (latitude, longitude) = try {
      locationRepository.getLastLocation()
    } catch (e: LocationNotFoundException) {
      return@withContext DEFAULT_LOCATION
    }
    return@withContext SearchLocation(null, null, latitude, longitude)
  }

  companion object {
    private val DEFAULT_LOCATION = SearchLocation("London", "London, UK", 51.509865, -0.118092)
  }
}
