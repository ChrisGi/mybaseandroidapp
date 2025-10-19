package gi.aera.domain.model

sealed interface AppError {
  data class BusinessError(val message: String?) : AppError
  data class HttpError(val errorCode: Int) : AppError
  data object NetworkError : AppError
  data object FatalError : AppError

  companion object {
    fun from(error: ApiResponse.Error) = when (error) {
      ApiResponse.Error.TimeoutError,
      ApiResponse.Error.NetworkError,
      -> NetworkError

      is ApiResponse.Error.HttpError -> HttpError(error.code)
      is ApiResponse.Error.SerializationError -> BusinessError(error.errorMessage)
      is ApiResponse.Error.UnknownError -> FatalError
    }

    fun from(exception: Throwable) = FatalError
  }
}
