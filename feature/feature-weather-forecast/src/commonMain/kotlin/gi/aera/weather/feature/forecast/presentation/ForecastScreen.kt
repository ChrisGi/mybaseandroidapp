package gi.aera.weather.feature.forecast.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import gi.aera.ui.LceState
import gi.aera.ui.LceViewState
import gi.aera.weather.feature.forecast.domain.ForecastViewState

@Composable
fun ForecastScreen(
  state: LceState<List<ForecastViewState>>,
  modifier: Modifier = Modifier
    .fillMaxSize(),
) {
  LceViewState(
    state = state,
    error = {
      println("Error $it")
    },
  ) { sevenDayForecast ->
    val todayForecast = sevenDayForecast.first()
    Column(
      modifier = modifier,
    ) {
      Box(
        modifier = Modifier
          .padding(PaddingValues(bottom = 32.dp))
          .weight(1f),
      ) {
        Temperature(todayForecast.currentTemperature)
      }

      Condition(
        icon = todayForecast.conditionIcon,
        description = todayForecast.condition.asString(),
        modifier = Modifier
          .padding(PaddingValues(vertical = 32.dp))
          .weight(2f),
      )

      Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
          .align(Alignment.CenterHorizontally)
          .padding(PaddingValues(vertical = 8.dp)),
      ) {
        Text(
          text = todayForecast.location.asString().uppercase(),
          color = MaterialTheme.colorScheme.onBackground,
          style = TextStyle(fontWeight = FontWeight.Bold),
          modifier = Modifier
            .align(Alignment.CenterVertically),
        )
      }

      Box(
        modifier = Modifier
          .weight(1f)
          .fillMaxWidth(),
      ) {
        WeeklyForecast(
          sevenDayForecast,
          Modifier
            .align(Alignment.Center),
        )
      }
    }
  }
}
