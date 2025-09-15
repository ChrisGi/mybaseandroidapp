package gi.aera.ui.text

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.stringResource

sealed class UiString {
  data object Empty : UiString()
  class Resource(val id: StringResource, vararg val args: Any) : UiString()
  class Text(val value: String) : UiString()

  @Suppress("SpreadOperator")
  @Composable
  fun asString(): String = when (this) {
    is Empty -> ""
    is Resource -> stringResource(id, *args)
    is Text -> value
  }

  @Suppress("SpreadOperator")
  suspend fun asStringAsync(): String = when (this) {
    is Empty -> ""
    is Resource -> getString(id, *args)
    is Text -> value
  }
}
