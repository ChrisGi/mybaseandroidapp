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
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.rememberUpdatedState

@Composable
fun <T> SwipeToDeleteContainer(
  item: T,
  onDelete: (T) -> Unit,
  animationDuration: Int = 500,
  content: @Composable (T) -> Unit,
) {
  var isRemoved by remember { mutableStateOf(false) }
  val latestOnDelete by rememberUpdatedState(onDelete)
  val state = rememberSwipeToDismissBoxState(
    confirmValueChange = { value ->
      if (value == SwipeToDismissBoxValue.EndToStart) {
        isRemoved = true
        true
      } else {
        false
      }
    },
  )

  LaunchedEffect(isRemoved) {
    if (isRemoved) {
      delay(animationDuration.toLong())
      latestOnDelete(item)
    }
  }

  AnimatedVisibility(
    visible = !isRemoved,
    exit = shrinkVertically(
      animationSpec = tween(durationMillis = animationDuration),
      shrinkTowards = Alignment.Top,
    ) + fadeOut(),
  ) {
    SwipeToDismissBox(
      state = state,
      enableDismissFromStartToEnd = false,
      backgroundContent = {
        DeleteBackground(swipeDismissState = state)
      },
    ) {
      content(item)
    }
  }
}

@Composable
private fun DeleteBackground(
  swipeDismissState: SwipeToDismissBoxState,
  backgroundShape: RoundedCornerShape = RoundedCornerShape(16.dp),
) {
  val color = if (swipeDismissState.dismissDirection == SwipeToDismissBoxValue.EndToStart) {
    Color.Red
  } else {
    Color.Transparent
  }

  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(color, backgroundShape)
      .padding(16.dp),
    contentAlignment = Alignment.CenterEnd,
  ) {
    Icon(
      imageVector = Icons.Default.Delete,
      contentDescription = null,
      tint = Color.White,
    )
  }
}
