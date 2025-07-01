package gi.aera.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

sealed class LceState<out T> {
  data object Loading : LceState<Nothing>()
  data class Content<T>(val content: T) : LceState<T>()
  data class Error(val throwable: Throwable) : LceState<Nothing>()
}

@Composable
fun <T> LceViewState(
  state: LceState<T>,
  error: @Composable (throwable: Throwable) -> Unit,
  modifier: Modifier = Modifier
    .fillMaxSize()
    .background(MaterialTheme.colorScheme.background),
  loading: @Composable () -> Unit = { FullscreenProgressIndicator(modifier) },
  content: @Composable (T) -> Unit,
) {
  when (state) {
    is LceState.Loading -> loading()

    is LceState.Content -> {
      content(state.content)
    }

    is LceState.Error -> {
      error(state.throwable)
    }
  }
}

@Composable
private fun FullscreenProgressIndicator(modifier: Modifier = Modifier) {
  Box(
    contentAlignment = Alignment.Center,
    modifier = modifier,
  ) {
    CircularProgressIndicator()
  }
}
