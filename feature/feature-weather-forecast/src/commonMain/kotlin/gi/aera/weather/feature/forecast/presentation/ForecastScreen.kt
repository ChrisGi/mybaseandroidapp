package gi.aera.weather.feature.forecast.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import gi.aera.ui.LceState
import gi.aera.ui.LceViewState
import gi.aera.ui.theme.extraColors
import gi.aera.weather.error.AppErrorContentProvider
import gi.aera.weather.feature.forecast.presentation.model.CurrentConditions
import gi.aera.weather.feature.forecast.presentation.model.ForecastScreenViewEvent
import gi.aera.weather.feature.forecast.presentation.model.WeatherConditions

@Suppress("LongMethod")
@Composable
fun ForecastScreen(
  currentWeatherState: LceState<CurrentConditions>,
  forecastState: LceState<List<WeatherConditions>>,
  event: (ForecastScreenViewEvent) -> Unit,
  modifier: Modifier = Modifier,
) {
  LceViewState(
    modifier = modifier
      .systemBarsPadding(),
    state = currentWeatherState,
    errorContent = {
      AppErrorContentProvider(
        it,
        Modifier
          .fillMaxSize()
          .padding(horizontal = 32.dp),
        onRetry = {
          event(ForecastScreenViewEvent.Retry)
        },
        onCheckNetwork = {
          event(ForecastScreenViewEvent.NavigateToNetworkSettings)
        },
      )
    },
  ) { currentConditions ->
    Column {
      Box(
        modifier = Modifier
          .padding(start = 16.dp, end = 16.dp)
          .background(
            MaterialTheme.extraColors.surfaceGradient,
            MaterialTheme.shapes.extraLarge,
          ),
      ) {
        Column(
          modifier = Modifier
            .verticalScroll(rememberScrollState()),
        ) {
          ForecastLocation(
            location = currentConditions.weatherConditions.location.asString(),
            onClick = { event(ForecastScreenViewEvent.NavigateToSearchLocation()) },
            modifier = Modifier
              .fillMaxWidth()
              .padding(top = 16.dp)
              .align(
                Alignment.CenterHorizontally,
              ),
          )

          Condition(
            state = currentConditions.weatherConditions,
            modifier = Modifier.fillMaxSize(),
          )

          Spacer(modifier = Modifier.padding(vertical = 16.dp))

          if (currentConditions.conditionValues.isNotEmpty()) {
            ConditionValues(
              state = currentConditions.conditionValues,
              modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            )
          }
        }
      }

      Box(
        modifier = Modifier
          .padding(top = 12.dp, start = 16.dp, end = 16.dp)
          .background(
            MaterialTheme.extraColors.surfaceGradientReversed,
            MaterialTheme.shapes.extraLarge,
          ),
      ) {
        Column(
          modifier = Modifier.fillMaxWidth(),
          verticalArrangement = Arrangement.SpaceBetween,
          horizontalAlignment = Alignment.CenterHorizontally,
        ) {
          WeeklyForecast(
            forecastState,
            Modifier
              .padding(16.dp),
          )
        }
      }
    }
  }
}
