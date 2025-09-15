package gi.aera.domain.model

sealed interface AppError {
  data class BusinessError(val message: String) : AppError
  data class NetworkError(val errorCode: Int) : AppError
  data object FatalError : AppError

  companion object {
    fun from(error: ApiResponse.Error) = when (error) {
      is ApiResponse.Error.HttpError -> NetworkError(error.code)
      is ApiResponse.Error.SerializationError,
        -> BusinessError(error.errorMessage ?: "Something went wrong")
      is ApiResponse.Error.UnknownError,
        -> FatalError
    }
  }
}
