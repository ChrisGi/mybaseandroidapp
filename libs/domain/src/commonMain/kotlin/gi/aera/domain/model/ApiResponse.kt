package gi.aera.domain.model

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

    data class UnknownError(val message: String?) : Error(message)
    data class SerializationError(val message: String?) : Error(message)
    data object NetworkError : Error(null)
    data object TimeoutError : Error(null)
  }
}
