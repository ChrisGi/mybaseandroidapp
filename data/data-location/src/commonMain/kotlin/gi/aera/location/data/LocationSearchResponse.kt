package gi.aera.location.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class LocationSearchResponse(
  val results: List<SearchResult>,
)

@Serializable
data class SearchResult(
  val datasource: Datasource,
  val country: String? = null,
  @SerialName("country_code")
  val countryCode: String? = null,
  val state: String? = null,
  val city: String? = null,
  @SerialName("iso3166_2")
  val iso31662: String? = null,
  val lon: Double,
  val lat: Double,
  @SerialName("result_type")
  val resultType: String? = null,
  val formatted: String? = null,
  @SerialName("address_line1")
  val addressLine1: String? = null,
  @SerialName("address_line2")
  val addressLine2: String? = null,
  val category: String? = null,
  val timezone: Timezone? = null,
  @SerialName("plus_code")
  val plusCode: String? = null,
  @SerialName("plus_code_short")
  val plusCodeShort: String? = null,
  val rank: Rank? = null,
  @SerialName("place_id")
  val placeId: String,
  val county: String? = null,
)

@Serializable
data class Datasource(
  val sourcename: String? = null,
  val attribution: String? = null,
  val license: String? = null,
  val url: String? = null,
)

@Serializable
data class Timezone(
  val name: String? = null,
  @SerialName("offset_STD")
  val offsetStd: String? = null,
  @SerialName("offset_STD_seconds")
  val offsetStdSeconds: Long? = null,
  @SerialName("offset_DST")
  val offsetDst: String? = null,
  @SerialName("offset_DST_seconds")
  val offsetDstSeconds: Long? = null,
  @SerialName("abbreviation_STD")
  val abbreviationStd: String? = null,
  @SerialName("abbreviation_DST")
  val abbreviationDst: String? = null,
)

@Serializable
data class Rank(
  val importance: Double? = null,
  val confidence: Double? = null,
  @SerialName("confidence_city_level")
  val confidenceCityLevel: Double? = null,
  @SerialName("match_type")
  val matchType: String? = null,
)
