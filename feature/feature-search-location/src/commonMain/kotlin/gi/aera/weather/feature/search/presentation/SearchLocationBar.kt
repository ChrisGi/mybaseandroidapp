package gi.aera.weather.feature.search.presentation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import gi.aera.weather.feature.search.domain.model.LocationSearchBarState

@Suppress("LongMethod")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchLocationBar(
  state: LocationSearchBarState,
  search: (query: String) -> Unit,
  modifier: Modifier = Modifier
    .fillMaxWidth()
    .padding(PaddingValues(horizontal = 16.dp, vertical = 8.dp)),
  resultContent: @Composable () -> Unit,
) {
  val colors = SearchBarDefaults.colors(
    containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
  )
  val focusManager = LocalFocusManager.current
  DockedSearchBar(
    inputField = {
      TextField(
        value = state.queryValue,
        onValueChange = { value ->
          search(value)
        },
        placeholder = {
          Text(state.placeholder.asString())
        },
        leadingIcon = {
          Icon(
            imageVector = Icons.Default.Search,
            contentDescription = null,
          )
        },
        trailingIcon = {
          if (state.queryValue.isNotEmpty()) {
            IconButton(
              onClick = {
                search("")
                focusManager.clearFocus()
              },
            ) {
              Icon(
                imageVector = Icons.Default.Clear,
                contentDescription = null,
              )
            }
          }
        },
        singleLine = true,
        colors = TextFieldDefaults.colors(
          focusedContainerColor = Color.Transparent,
          unfocusedContainerColor = Color.Transparent,
          disabledContainerColor = Color.Transparent,
          focusedIndicatorColor = Color.Transparent,
          unfocusedIndicatorColor = Color.Transparent,
        ),
        modifier = Modifier
          .fillMaxWidth(),
      )
    },
    expanded = state.expanded,
    onExpandedChange = { },
    shape = RoundedCornerShape(16.dp),
    colors = colors,
    tonalElevation = 2.dp,
    shadowElevation = 4.dp,
    modifier = modifier,
    content = {
      resultContent()
    },
  )
}
