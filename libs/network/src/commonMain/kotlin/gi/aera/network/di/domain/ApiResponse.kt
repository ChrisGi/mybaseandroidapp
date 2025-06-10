package gi.aera.network.di.domain

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.statement.HttpResponse
import io.ktor.utils.io.CancellationException
import kotlinx.serialization.SerializationException

sealed class ApiResponse<out T> {

  data class Success<T>(val data: T) : ApiResponse<T>()

  sealed class Error(open val errorMessage: String?) : ApiResponse<Nothing>() {

    class HttpError(
      val code: Int,
      val errorBody: String?,
      override val errorMessage: String?,
    ) : Error(errorMessage) {
      override fun toString(): String = "$code $errorMessage $errorBody"
    }

    data class GenericError(val message: String?) : Error(message)
    data class SerializationError(val message: String?) : Error(message)
  }
}

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
    ApiResponse.Error.GenericError(e.message)
  }
