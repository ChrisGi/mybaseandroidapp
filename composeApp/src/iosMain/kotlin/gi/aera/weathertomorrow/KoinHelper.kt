package gi.aera.weathertomorrow

import org.koin.core.context.startKoin

fun InitKoin() {
  startKoin {
    modules(appModules)
  }
}
