package gi.aera.weather.feature.forecast.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import gi.aera.weather.error.AppErrorContentProvider
import gi.aera.weather.feature.forecast.domain.ForecastScreenViewEvent
import gi.aera.weather.feature.forecast.domain.WeatherConditions

@Suppress("LongMethod")
@Composable
fun ForecastScreen(
  currentWeatherState: LceState<WeatherConditions>,
  forecastState: LceState<List<WeatherConditions>>,
  event: (ForecastScreenViewEvent) -> Unit,
  modifier: Modifier = Modifier,
) {
  LceViewState(
    modifier = modifier,
    state = currentWeatherState,
    errorContent = {
      AppErrorContentProvider(
        it,
        Modifier
          .fillMaxSize()
          .padding(vertical = 64.dp, horizontal = 32.dp),
        onRetry = {
          event(ForecastScreenViewEvent.Retry)
        },
        onCheckNetwork = {
          event(ForecastScreenViewEvent.NavigateToNetworkSettings)
        },
      )
    },
  ) { todayForecast ->
    Column {
      Box(
        modifier = Modifier
          .padding(top = 64.dp, start = 32.dp)
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
          .weight(2f),
      )

      ForecastLocation(
        location = todayForecast.location.asString(),
        onClick = { event(ForecastScreenViewEvent.NavigateToSearchLocation()) },
        modifier = Modifier
          .fillMaxWidth()
          .align(Alignment.CenterHorizontally)
          .padding(16.dp)
          .clickable { event(ForecastScreenViewEvent.NavigateToSearchLocation()) },
      )

      Box(
        modifier = Modifier
          .weight(1f)
          .padding(horizontal = 16.dp)
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
