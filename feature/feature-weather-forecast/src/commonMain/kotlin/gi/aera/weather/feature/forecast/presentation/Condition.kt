package gi.aera.weather.feature.forecast.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun Condition(
  icon: DrawableResource,
  description: String,
  modifier: Modifier = Modifier,
) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = modifier,
  ) {
    Image(
      painter = painterResource(icon),
      contentDescription = null,
      modifier = Modifier
        .fillMaxSize()
        .align(Alignment.CenterVertically)
        .weight(1f)
        .padding(32.dp),
    )

    Column(
      modifier = Modifier
        .padding(horizontal = 16.dp)
        .align(Alignment.CenterVertically),
    ) {
      description
        .uppercase()
        .forEach { char ->
          Text(
            color = MaterialTheme.colorScheme.onBackground,
            text = char.toString(),
            style = TextStyle(fontWeight = FontWeight.Bold),
            modifier = Modifier
              .padding(vertical = 2.dp)
              .align(Alignment.CenterHorizontally),
          )
        }
    }
  }
}
