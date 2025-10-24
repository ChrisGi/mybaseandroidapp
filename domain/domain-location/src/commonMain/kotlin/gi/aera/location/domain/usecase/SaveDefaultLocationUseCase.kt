package gi.aera.location.domain.usecase

import gi.aera.location.domain.repository.DefaultLocationRepository
import gi.aera.location.domain.model.SearchLocation
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class SaveDefaultLocationUseCase internal constructor(
  private val defaultLocationRepository: DefaultLocationRepository,
  private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) {

  suspend operator fun invoke(location: SearchLocation) = withContext(dispatcher) {
    runCatching {
      defaultLocationRepository.saveLocation(location)
    }
  }
}
