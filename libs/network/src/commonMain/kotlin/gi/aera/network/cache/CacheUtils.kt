package gi.aera.network.cache

import io.ktor.client.request.HttpRequestBuilder

fun HttpRequestBuilder.applyCacheControl(forceFreshData: Boolean) {
  if (forceFreshData) {
    headers.append("Cache-Control", "no-cache")
  }
}
