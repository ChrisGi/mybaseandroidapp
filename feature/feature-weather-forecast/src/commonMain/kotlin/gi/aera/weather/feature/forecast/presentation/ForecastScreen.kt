package gi.aera.weather.feature.forecast.presentation

import androidx.compose.foundation.clickable
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
import gi.aera.weather.feature.forecast.domain.Forecast
import gi.aera.weather.feature.forecast.domain.ForecastScreenViewEvent

@Composable
fun ForecastScreen(
  state: LceState<List<Forecast>>,
  event: (ForecastScreenViewEvent) -> Unit,
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

      ForecastLocation(
        location = todayForecast.location.asString(),
        onClick = { event.invoke(ForecastScreenViewEvent.NavigateToSearchLocation) },
        modifier = Modifier
          .fillMaxWidth()
          .align(Alignment.CenterHorizontally)
          .padding(8.dp)
          .clickable { event.invoke(ForecastScreenViewEvent.NavigateToSearchLocation) },
      )

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
