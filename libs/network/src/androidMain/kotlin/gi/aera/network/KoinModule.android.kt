package gi.aera.network

import android.content.Context
import android.net.ConnectivityManager
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.cache.storage.CacheStorage
import io.ktor.client.plugins.cache.storage.FileStorage
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module

actual val platformModule: Module = module {
  single { FileStorage(androidContext().cacheDir) } bind CacheStorage::class
  single { HttpClient(OkHttp) }
  single { androidContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager }
}
