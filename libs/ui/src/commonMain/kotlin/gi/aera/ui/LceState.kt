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
  data class Success<T>(val data: T) : LceState<T>()
  data class Error(val throwable: Throwable) : LceState<Nothing>()
}

@Composable
fun <T> LceViewState(
  state: LceState<T>,
  error: @Composable (throwable: Throwable) -> Unit,
  content: @Composable (T) -> Unit
) {
  when (state) {
    is LceState.Loading -> {
      Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
          .fillMaxSize()
          .background(MaterialTheme.colorScheme.background)
      ) {
        CircularProgressIndicator()
      }
    }

    is LceState.Success -> {
      content(state.data)
    }

    is LceState.Error -> {
      error(state.throwable)
    }
  }
}
