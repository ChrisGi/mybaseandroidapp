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
import gi.aera.weather.component.Temperature
import gi.aera.weather.feature.forecast.domain.WeatherConditions
import gi.aera.weather.feature.forecast.domain.ForecastScreenViewEvent

@Composable
fun ForecastScreen(
  currentWeatherState: LceState<WeatherConditions>,
  forecastState: LceState<List<WeatherConditions>>,
  event: (ForecastScreenViewEvent) -> Unit,
  modifier: Modifier = Modifier
    .fillMaxSize(),
) {
  LceViewState(
    state = currentWeatherState,
    error = {
      println("Error $it")
    },
  ) { todayForecast ->
    Column(
      modifier = modifier,
    ) {
      Box(
        modifier = Modifier
          .padding(PaddingValues(bottom = 32.dp))
          .weight(1f),
      ) {
        Temperature(
          temperature = todayForecast.temperature,
          unit = todayForecast.unitSystem,
          modifier = Modifier.fillMaxSize(),
        )
      }

      Condition(
        icon = todayForecast.conditionIcon,
        description = todayForecast.conditionTitle.asString(),
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
          forecastState,
          Modifier
            .align(Alignment.Center),
        )
      }
    }
  }
}
