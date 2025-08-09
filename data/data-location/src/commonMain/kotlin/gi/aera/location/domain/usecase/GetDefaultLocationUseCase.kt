package gi.aera.location.domain.usecase

import gi.aera.location.data.DefaultLocationRepository
import gi.aera.location.domain.model.PermissionException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.withContext

class GetDefaultLocationUseCase internal constructor(
  private val defaultLocationRepository: DefaultLocationRepository,
  private val getLastLocationUseCase: GetLastLocationUseCase,
  private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) {

  @Suppress("SwallowedException", "TooGenericExceptionCaught")
  suspend operator fun invoke() = withContext(dispatcher) {
    defaultLocationRepository.getLocation()
      .catch { _ ->
        try {
          emit(getLastLocationUseCase())
        } catch (e: Exception) {
          throw PermissionException()
        }
      }
  }
}
