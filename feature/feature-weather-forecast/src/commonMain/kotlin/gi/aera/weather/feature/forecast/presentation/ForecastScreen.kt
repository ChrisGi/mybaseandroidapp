package gi.aera.weather.feature.forecast.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import gi.aera.ui.LceState
import gi.aera.ui.LceViewState
import gi.aera.weather.feature.forecast.domain.ForecastViewState
import org.jetbrains.compose.resources.stringResource

@Composable
fun ForecastScreen(state: LceState<List<ForecastViewState>>) {
  LceViewState(
    state = state,
    error = {
      println("Error $it")
    },
  ) { sevenDayForecast ->
    val todayForecast = sevenDayForecast.first()
    Column(
      modifier = Modifier
        .fillMaxSize()
    ) {
      Box(
        modifier = Modifier
          .padding(PaddingValues(bottom = 32.dp))
          .weight(1f)
      ) {
        Temperature(todayForecast.currentTemperature)
      }

      Condition(
        icon = todayForecast.conditionIcon,
        description = stringResource(todayForecast.condition),
        modifier = Modifier
          .padding(PaddingValues(vertical = 32.dp))
          .weight(2f)
      )

      Box(
        modifier = Modifier
          .weight(1f)
          .fillMaxWidth()
      ) {
        WeeklyForecast(
          sevenDayForecast,
          Modifier
            .align(Alignment.Center)
        )
      }
    }
  }
}
