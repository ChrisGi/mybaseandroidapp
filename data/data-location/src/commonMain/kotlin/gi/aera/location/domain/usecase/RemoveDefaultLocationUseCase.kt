package gi.aera.location.domain.usecase

import gi.aera.location.domain.model.DefaultLocationRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class RemoveDefaultLocationUseCase internal constructor(
  private val defaultLocationRepository: DefaultLocationRepository,
  private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) {

  suspend operator fun invoke() = withContext(dispatcher) {
    runCatching {
      defaultLocationRepository.removeLocation()
    }
  }
}
