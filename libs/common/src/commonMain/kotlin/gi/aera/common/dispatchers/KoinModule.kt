@file:Suppress("TopLevelPropertyNaming")
package gi.aera.common.dispatchers

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

val dispatchersKoinModule = module {
  single(named(IoDispatcher)) { Dispatchers.IO } bind CoroutineDispatcher::class
  single(named(MainDispatcher)) { Dispatchers.Main } bind CoroutineDispatcher::class
  single(named(DefaultDispatcher)) { Dispatchers.Default } bind CoroutineDispatcher::class
  single(named(UnconfinedDispatcher)) { Dispatchers.Unconfined } bind CoroutineDispatcher::class
}

const val IoDispatcher = "Dispatchers.IO"
const val MainDispatcher = "Dispatchers.Main"
const val DefaultDispatcher = "Dispatchers.Default"
const val UnconfinedDispatcher = "Dispatchers.Unconfined"
