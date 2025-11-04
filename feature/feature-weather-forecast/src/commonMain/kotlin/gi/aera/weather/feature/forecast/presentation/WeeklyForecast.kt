package gi.aera.weather.feature.forecast.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import gi.aera.ui.LceState
import gi.aera.ui.LceViewState
import gi.aera.ui.theme.extraColors
import gi.aera.weather.Res
import gi.aera.weather.feature.forecast.presentation.model.WeatherConditions
import gi.aera.weather.weather_temperature_degree
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun WeeklyForecast(
  state: LceState<List<WeatherConditions>>,
  modifier: Modifier = Modifier,
) {
  val textColor = MaterialTheme.colorScheme.onBackground
  LceViewState(
    state = state,
    errorContent = {
      println("Error $it")
    },
  ) { forecast ->
    LazyRow(
      modifier = modifier,
      contentPadding = PaddingValues(8.dp),
    ) {
      items(forecast) { forecast ->
        Column(
          horizontalAlignment = Alignment.CenterHorizontally,
          modifier = Modifier
            .width(60.dp)
            .padding(horizontal = 2.dp)
            .background(MaterialTheme.extraColors.surfaceGradientReversed, RoundedCornerShape(16.dp)),
        ) {
          Text(
            color = textColor,
            text = forecast.moment,
            modifier = Modifier
              .align(Alignment.CenterHorizontally),
          )
          Image(
            painter = painterResource(forecast.conditionIcon),
            contentDescription = null,
            modifier = Modifier
              .size(48.dp)
              .align(Alignment.CenterHorizontally)
              .padding(horizontal = 4.dp),
          )
          Text(
            color = textColor,
            text = stringResource(Res.string.weather_temperature_degree, forecast.temperature),
            modifier = Modifier
              .align(Alignment.CenterHorizontally),
          )
        }
      }
    }
  }
}
