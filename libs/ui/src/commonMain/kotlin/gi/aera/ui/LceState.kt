package gi.aera.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import gi.aera.domain.model.AppError

sealed class LceState<out T> {
  data object Loading : LceState<Nothing>()
  data class Content<T>(val content: T) : LceState<T>()
  data class Error(val appError: AppError) : LceState<Nothing>()
}

@Composable
fun <T> LceViewState(
  state: LceState<T>,
  modifier: Modifier = Modifier,
  errorContent: @Composable (appError: AppError) -> Unit = {},
  loading: @Composable () -> Unit = { FullscreenProgressIndicator(modifier.fillMaxSize()) },
  content: @Composable (T) -> Unit,
) {
  when (state) {
    is LceState.Loading -> loading()

    is LceState.Content -> content(state.content)

    is LceState.Error -> errorContent(state.appError)
  }
}

@Composable
fun FullscreenProgressIndicator(modifier: Modifier = Modifier) {
  Box(
    contentAlignment = Alignment.Center,
    modifier = modifier,
  ) {
    CircularProgressIndicator()
  }
}
