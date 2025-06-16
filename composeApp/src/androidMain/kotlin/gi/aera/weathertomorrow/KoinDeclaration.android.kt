package gi.aera.weathertomorrow

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.KoinAppDeclaration

actual val koinAppDeclaration: KoinAppDeclaration = {
  androidContext(ApplicationContext.application)
}
