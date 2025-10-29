package gi.aera.location.data

import gi.aera.common.model.ApiResponse
import gi.aera.location.domain.model.LocationSearchResponse
import gi.aera.location.domain.repository.SearchLocationRepository
import kotlinx.serialization.json.Json

class StubSearchLocationRepositoryImpl : SearchLocationRepository {

  @Suppress("LongMethod")
  override suspend fun searchLocation(query: String): ApiResponse<LocationSearchResponse> {
    val json = "{\n" +
      "  \"results\": [\n" +
      "    {\n" +
      "      \"datasource\": {\n" +
      "        \"sourcename\": \"openstreetmap\",\n" +
      "        \"attribution\": \"© OpenStreetMap contributors\",\n" +
      "        \"license\": \"Open Database License\",\n" +
      "        \"url\": \"https://www.openstreetmap.org/copyright\"\n" +
      "      },\n" +
      "      \"country\": \"Azerbejdżan\",\n" +
      "      \"country_code\": \"az\",\n" +
      "      \"county\": \"Qazax rayonu\",\n" +
      "      \"city\": \"Qazax\",\n" +
      "      \"iso3166_2\": \"AZ-QAZ\",\n" +
      "      \"lon\": 45.361029,\n" +
      "      \"lat\": 41.0911532,\n" +
      "      \"result_type\": \"city\",\n" +
      "      \"formatted\": \"Qazax, Azerbejdżan\",\n" +
      "      \"address_line1\": \"Qazax\",\n" +
      "      \"address_line2\": \"Azerbejdżan\",\n" +
      "      \"category\": \"populated_place\",\n" +
      "      \"timezone\": {\n" +
      "        \"name\": \"Asia/Baku\",\n" +
      "        \"offset_STD\": \"+04:00\",\n" +
      "        \"offset_STD_seconds\": 14400,\n" +
      "        \"offset_DST\": \"+04:00\",\n" +
      "        \"offset_DST_seconds\": 14400\n" +
      "      },\n" +
      "      \"plus_code\": \"8HH739R6+FC\",\n" +
      "      \"plus_code_short\": \"R6+FC Qazax, Qazax rayonu, Azerbejdżan\",\n" +
      "      \"rank\": {\n" +
      "        \"importance\": 0.45978267422065044,\n" +
      "        \"confidence\": 0.2727272727272727,\n" +
      "        \"confidence_city_level\": 0.2727272727272727,\n" +
      "        \"match_type\": \"full_match\"\n" +
      "      },\n" +
      "      \"place_id\": \"512cf4c13236ae4640597f7676e8aa8b4440f00102f901bc22044e00000000c00208\",\n" +
      "      \"bbox\": {\n" +
      "        \"lon1\": 45.3354405,\n" +
      "        \"lat1\": 41.0813156,\n" +
      "        \"lon2\": 45.3897694,\n" +
      "        \"lat2\": 41.1131322\n" +
      "      }\n" +
      "    },\n" +
      "    {\n" +
      "      \"datasource\": {\n" +
      "        \"sourcename\": \"openstreetmap\",\n" +
      "        \"attribution\": \"© OpenStreetMap contributors\",\n" +
      "        \"license\": \"Open Database License\",\n" +
      "        \"url\": \"https://www.openstreetmap.org/copyright\"\n" +
      "      },\n" +
      "      \"country\": \"Azerbejdżan\",\n" +
      "      \"country_code\": \"az\",\n" +
      "      \"county\": \"Qazax rayonu\",\n" +
      "      \"postcode\": \"3500\",\n" +
      "      \"iso3166_2\": \"AZ-QAZ\",\n" +
      "      \"lon\": 45.25288326842112,\n" +
      "      \"lat\": 41.17073655,\n" +
      "      \"state\": \"Qazakh District\",\n" +
      "      \"result_type\": \"postcode\",\n" +
      "      \"formatted\": \"Qazax rayonu, Azerbejdżan\",\n" +
      "      \"address_line1\": \"Qazax rayonu\",\n" +
      "      \"address_line2\": \"Azerbejdżan\",\n" +
      "      \"category\": \"administrative\",\n" +
      "      \"timezone\": {\n" +
      "        \"name\": \"Asia/Baku\",\n" +
      "        \"offset_STD\": \"+04:00\",\n" +
      "        \"offset_STD_seconds\": 14400,\n" +
      "        \"offset_DST\": \"+04:00\",\n" +
      "        \"offset_DST_seconds\": 14400\n" +
      "      },\n" +
      "      \"plus_code\": \"8HH757C3+75\",\n" +
      "      \"rank\": {\n" +
      "        \"importance\": 0.5027037529839511,\n" +
      "        \"confidence\": 0.2727272727272727,\n" +
      "        \"confidence_city_level\": 0.2727272727272727,\n" +
      "        \"match_type\": \"full_match\"\n" +
      "      },\n" +
      "      \"place_id\": \"5183c99b7a5ea0464059ae3dfdb1da954440f00101f901a87a3c0000000000c00207920307333530302b617a\",\n" +
      "      \"bbox\": {\n" +
      "        \"lon1\": 45.0149428,\n" +
      "        \"lat1\": 40.9966853,\n" +
      "        \"lon2\": 45.3907042,\n" +
      "        \"lat2\": 41.3456441\n" +
      "      }\n" +
      "    },\n" +
      "    {\n" +
      "      \"datasource\": {\n" +
      "        \"sourcename\": \"openstreetmap\",\n" +
      "        \"attribution\": \"© OpenStreetMap contributors\",\n" +
      "        \"license\": \"Open Database License\",\n" +
      "        \"url\": \"https://www.openstreetmap.org/copyright\"\n" +
      "      },\n" +
      "      \"country\": \"Iran\",\n" +
      "      \"country_code\": \"ir\",\n" +
      "      \"county\": \"Kazwin\",\n" +
      "      \"iso3166_2\": \"IR-26\",\n" +
      "      \"state\": \"Kazwin\",\n" +
      "      \"lon\": 49.8398161,\n" +
      "      \"lat\": 36.0156291,\n" +
      "      \"result_type\": \"county\",\n" +
      "      \"formatted\": \"Kazwin, Iran\",\n" +
      "      \"address_line1\": \"Kazwin\",\n" +
      "      \"address_line2\": \"Iran\",\n" +
      "      \"category\": \"administrative\",\n" +
      "      \"timezone\": {\n" +
      "        \"name\": \"Asia/Tehran\",\n" +
      "        \"offset_STD\": \"+03:30\",\n" +
      "        \"offset_STD_seconds\": 12600,\n" +
      "        \"offset_DST\": \"+04:30\",\n" +
      "        \"offset_DST_seconds\": 16200\n" +
      "      },\n" +
      "      \"plus_code\": \"8H8F2R8Q+7W\",\n" +
      "      \"rank\": {\n" +
      "        \"importance\": 0.5772294215436352,\n" +
      "        \"confidence\": 0,\n" +
      "        \"match_type\": \"full_match\"\n" +
      "      },\n" +
      "      \"place_id\": \"51bf130e187feb484059d6ae642200024240f00101f9013faf030000000000c00209\",\n" +
      "      \"bbox\": {\n" +
      "        \"lon1\": 48.7244712,\n" +
      "        \"lat1\": 35.3905895,\n" +
      "        \"lon2\": 50.8820575,\n" +
      "        \"lat2\": 36.8151799\n" +
      "      }\n" +
      "    },\n" +
      "    {\n" +
      "      \"datasource\": {\n" +
      "        \"sourcename\": \"openstreetmap\",\n" +
      "        \"attribution\": \"© OpenStreetMap contributors\",\n" +
      "        \"license\": \"Open Database License\",\n" +
      "        \"url\": \"https://www.openstreetmap.org/copyright\"\n" +
      "      },\n" +
      "      \"country\": \"Iran\",\n" +
      "      \"country_code\": \"ir\",\n" +
      "      \"state\": \"Kazwin\",\n" +
      "      \"county\": \"شهرستان قزوین\",\n" +
      "      \"city\": \"Kazwin\",\n" +
      "      \"district\": \"بخش مرکزی قزوین\",\n" +
      "      \"iso3166_2\": \"IR-26\",\n" +
      "      \"lon\": 50.006663,\n" +
      "      \"lat\": 36.2804074,\n" +
      "      \"result_type\": \"district\",\n" +
      "      \"formatted\": \"بخش مرکزی قزوین, Kazwin, Iran\",\n" +
      "      \"address_line1\": \"بخش مرکزی قزوین\",\n" +
      "      \"address_line2\": \"Kazwin, Iran\",\n" +
      "      \"category\": \"administrative\",\n" +
      "      \"timezone\": {\n" +
      "        \"name\": \"Asia/Tehran\",\n" +
      "        \"offset_STD\": \"+03:30\",\n" +
      "        \"offset_STD_seconds\": 12600,\n" +
      "        \"offset_DST\": \"+04:30\",\n" +
      "        \"offset_DST_seconds\": 16200\n" +
      "      },\n" +
      "      \"plus_code\": \"8H8G72J4+5M\",\n" +
      "      \"rank\": {\n" +
      "        \"importance\": 0.565583114741218,\n" +
      "        \"confidence\": 0,\n" +
      "        \"match_type\": \"full_match\"\n" +
      "      },\n" +
      "      \"place_id\": \"51f08b4b55da004940593847c263e4234240f00101f90165a3670000000000c00206\",\n" +
      "      \"bbox\": {\n" +
      "        \"lon1\": 49.9507207,\n" +
      "        \"lat1\": 36.2308454,\n" +
      "        \"lon2\": 50.081179,\n" +
      "        \"lat2\": 36.3324158\n" +
      "      }\n" +
      "    },\n" +
      "    {\n" +
      "      \"datasource\": {\n" +
      "        \"sourcename\": \"openstreetmap\",\n" +
      "        \"attribution\": \"© OpenStreetMap contributors\",\n" +
      "        \"license\": \"Open Database License\",\n" +
      "        \"url\": \"https://www.openstreetmap.org/copyright\"\n" +
      "      },\n" +
      "      \"country\": \"Iran\",\n" +
      "      \"country_code\": \"ir\",\n" +
      "      \"state\": \"Azerbejdżan Zachodni\",\n" +
      "      \"county\": \"شهرستان ارومیه\",\n" +
      "      \"city\": \"\\n\"\n" +
      "    }\n" +
      "  ]\n" +
      "}"
    val response = Json.decodeFromString(LocationSearchResponse.serializer(), json)
    return ApiResponse.Success(response)
  }
}
