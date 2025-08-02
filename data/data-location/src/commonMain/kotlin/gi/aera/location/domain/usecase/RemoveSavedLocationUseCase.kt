package gi.aera.location.domain.usecase

import gi.aera.location.data.SaveLocationRepository
import gi.aera.location.domain.model.SearchLocation
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class RemoveSavedLocationUseCase internal constructor(
  private val saveLocationRepository: SaveLocationRepository,
  private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) {

  suspend operator fun invoke(location: SearchLocation) = withContext(dispatcher) {
    runCatching { saveLocationRepository.removeLocation(location.placeId) }
  }
}
