package gi.aera.weathertomorrow

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class WeatherApplication : Application() {

  override fun onCreate() {
    super.onCreate()
    startKoin {
      androidContext(this@WeatherApplication)
      androidLogger()
      modules(appModules)
    }
  }
}
