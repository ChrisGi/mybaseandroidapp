package gi.aera.weather.error

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import gi.aera.domain.model.AppError
import gi.aera.ui.text.UiString
import gi.aera.weather.Res
import gi.aera.weather.error_fatal
import gi.aera.weather.error_network
import gi.aera.weather.error_retry
import org.jetbrains.compose.resources.stringResource

@Composable
fun AppErrorContentProvider(
  appError: AppError,
  modifier: Modifier = Modifier,
  onRetry: () -> Unit = {},
) {
  when (appError) {
    is AppError.BusinessError -> BusinessError(appError, modifier)
    is AppError.NetworkError -> NetworkError(appError, modifier, onRetry)
    is AppError.FatalError -> FatalError(modifier = modifier)
  }
}

@Composable
fun BusinessError(
  appError: AppError.BusinessError,
  modifier: Modifier = Modifier,
) {
  ErrorComponent(
    message = appError.message,
    icon = {
      Image(
        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.error),
        imageVector = Icons.Filled.Warning,
        contentDescription = null,
        modifier = Modifier
          .fillMaxWidth()
          .height(120.dp),
      )
    },
    modifier = modifier,
  )
}

@Composable
fun NetworkError(
  appError: AppError.NetworkError,
  modifier: Modifier = Modifier,
  onBack: () -> Unit = {},
) {
  val message = remember { UiString.Resource(Res.string.error_network, appError.errorCode) }
  Column(
    modifier = modifier,
  ) {
    ErrorComponent(
      message = message.asString(),
      icon = {
        Image(
          colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.error),
          imageVector = Icons.Filled.CloudOff,
          contentDescription = null,
          modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        )
      },
    )
    Button(
      onClick = { onBack() },
      modifier = Modifier
        .fillMaxWidth(),
    ) {
      Text(text = stringResource(Res.string.error_retry))
    }
  }
}

@Composable
fun FatalError(
  modifier: Modifier = Modifier,
  message: UiString? = null,
) {
  val uiMessage = remember { message ?: UiString.Resource(Res.string.error_fatal) }
  Column(
    modifier = modifier,
  ) {
    ErrorComponent(
      message = uiMessage.asString(),
      icon = {
        Image(
          colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.error),
          imageVector = Icons.Outlined.ErrorOutline,
          contentDescription = null,
          modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        )
      },
    )
  }
}
