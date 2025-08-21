package gi.aera.location.domain.usecase

import gi.aera.location.data.DefaultLocationRepository
import gi.aera.location.domain.model.LocationNotFoundException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn

class GetDefaultLocationUseCase internal constructor(
  private val defaultLocationRepository: DefaultLocationRepository,
  private val getLastLocationUseCase: GetLastLocationUseCase,
  private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) {

  operator fun invoke() = defaultLocationRepository.getLocation()
    .catch { e ->
      when {
        e is LocationNotFoundException -> emit(getLastLocationUseCase())
        else -> throw e
      }
    }
    .flowOn(dispatcher)
}
