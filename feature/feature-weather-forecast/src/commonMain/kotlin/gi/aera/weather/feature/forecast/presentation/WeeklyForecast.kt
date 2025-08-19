package gi.aera.weather.feature.forecast.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import gi.aera.ui.LceState
import gi.aera.ui.LceViewState
import gi.aera.weather.Res
import gi.aera.weather.feature.forecast.domain.WeatherConditions
import gi.aera.weather.weather_temperature_degree
import org.jetbrains.compose.resources.stringResource

@Composable
fun WeeklyForecast(
  state: LceState<List<WeatherConditions>>,
  modifier: Modifier = Modifier,
) {

  LceViewState(
    state = state,
    error = {
      println("Error $it")
    },
  ) { forecast ->
    LazyRow(
      modifier = modifier,
    ) {
      items(forecast) { forecast ->
        Column(
          modifier = Modifier
            .width(60.dp),
        ) {
          Text(
            color = MaterialTheme.colorScheme.onBackground,
            text = forecast.weekday,
            modifier = Modifier
              .align(Alignment.CenterHorizontally),
          )
          if (!LocalInspectionMode.current) {
            AsyncImage(
              colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground),
              model = Res.getUri(forecast.conditionIcon),
              contentDescription = null,
              modifier = Modifier
                .size(48.dp)
                .align(Alignment.CenterHorizontally)
                .padding(horizontal = 8.dp),
            )
          }
          Text(
            color = MaterialTheme.colorScheme.onBackground,
            text = stringResource(Res.string.weather_temperature_degree, forecast.temperature),
            modifier = Modifier
              .align(Alignment.CenterHorizontally),
          )
        }
      }
    }
  }
}
