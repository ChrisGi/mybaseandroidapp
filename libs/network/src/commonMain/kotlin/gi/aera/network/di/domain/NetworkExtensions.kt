package gi.aera.network.di.domain

import gi.aera.domain.model.ApiResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.statement.HttpResponse
import kotlinx.serialization.SerializationException
import kotlin.coroutines.cancellation.CancellationException

@Suppress("TooGenericExceptionCaught")
suspend inline fun <reified T> HttpClient.apiRequest(
  block: () -> HttpResponse,
): ApiResponse<T> =
  try {
    ApiResponse.Success(block().body())
  } catch (exception: ClientRequestException) {
    ApiResponse.Error.HttpError(
      code = exception.response.status.value,
      errorBody = exception.response.body(),
      errorMessage = exception.message,
    )
  } catch (e: SerializationException) {
    ApiResponse.Error.SerializationError(e.message)
  } catch (c: CancellationException) {
    throw c
  } catch (e: Exception) {
    ApiResponse.Error.UnknownError(e.message)
  }

inline fun <T, S> ApiResponse<T>.map(crossinline mapper: (T) -> S): ApiResponse<S> {
  return when (this) {
    is ApiResponse.Error -> this
    is ApiResponse.Success<*> -> mapper(data as T).let { ApiResponse.Success(it) }
  }
}
