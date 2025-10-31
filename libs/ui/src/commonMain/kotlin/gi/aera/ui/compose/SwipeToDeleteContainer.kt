package gi.aera.ui.compose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

private const val THRESHOLD = 0.5f

@Composable
fun <T : SwipeableItem> SwipeToDeleteContainer(
  item: T,
  onDelete: (T) -> Unit,
  animationDuration: Int = 500,
  content: @Composable (T) -> Unit,
) {
  var isVisible by remember { mutableStateOf(true) }
  val latestOnDelete by rememberUpdatedState(onDelete)

  val currentItem by rememberUpdatedState(item)
  val dismissState = rememberSwipeToDismissBoxState(
    positionalThreshold = { it * THRESHOLD },
    confirmValueChange = {
      if (it == SwipeToDismissBoxValue.EndToStart) {
        isVisible = false
        true
      }
      false
    },
  )

  LaunchedEffect(isVisible) {
    if (!isVisible) {
      delay(animationDuration.toLong())
      latestOnDelete(currentItem)
    }
  }

  AnimatedVisibility(
    visible = isVisible,
    exit = shrinkVertically(
      animationSpec = tween(durationMillis = animationDuration),
      shrinkTowards = Alignment.Top,
    ) + fadeOut(),
  ) {
    SwipeToDismissBox(
      state = dismissState,
      enableDismissFromStartToEnd = false,
      enableDismissFromEndToStart = item.isSwipeable(),
      backgroundContent = { DeleteBackground() },
      content = { content(currentItem) },
    )
  }
}

@Composable
private fun DeleteBackground(
  backgroundShape: RoundedCornerShape = RoundedCornerShape(16.dp),
) {
  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.errorContainer, backgroundShape)
      .padding(16.dp),
    contentAlignment = Alignment.CenterEnd,
  ) {
    Icon(
      imageVector = Icons.Default.Delete,
      contentDescription = null,
      tint = MaterialTheme.colorScheme.onErrorContainer,
    )
  }
}
