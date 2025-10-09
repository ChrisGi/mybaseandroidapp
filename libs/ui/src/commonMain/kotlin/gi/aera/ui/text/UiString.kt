package gi.aera.ui.text

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.stringResource

sealed class UiString {
  data class Resource(val id: StringResource, val args: List<Any>) : UiString() {
    constructor(id: StringResource, vararg args: Any) : this(id, args.toList())
  }
  data class Text(val value: String) : UiString()
  data object Empty : UiString()

  @Suppress("SpreadOperator")
  @Composable
  fun asString(): String = when (this) {
    is Empty -> ""
    is Resource -> stringResource(id, *args.toTypedArray())
    is Text -> value
  }

  @Suppress("SpreadOperator")
  suspend fun asStringAsync(): String = when (this) {
    is Empty -> ""
    is Resource -> getString(id, *args.toTypedArray())
    is Text -> value
  }
}

fun UiString.getText() = when (this) {
  is UiString.Text -> value
  else -> error("Cannot get text from non-text UiString")
}
