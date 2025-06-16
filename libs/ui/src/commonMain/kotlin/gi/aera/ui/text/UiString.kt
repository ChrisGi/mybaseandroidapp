package gi.aera.ui.text

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

sealed class UiString {
  data object Empty : UiString()
  class Resource(val id: StringResource, vararg val args: Any) : UiString()
  class Text(val value: String) : UiString()

  @Suppress("SpreadOperator")
  @Composable
  fun asString(): String {
    return when (this) {
      is Empty -> ""
      is Resource -> stringResource(id, *args)
      is Text -> value
    }
  }
}
