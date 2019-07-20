package com.lew.vostracker

import org.json.JSONObject

class VoSService {
    val VOS_DATA_SOURCE_URL = "https://runescape.wiki/api.php"

    fun requestCurrentVoices(): JSONObject {
        val params = mapOf(
            "action" to "query",
            "format" to "json",
            "meta" to "allmessages",
            "formatversion" to "2",
            "ammessages" to "VoS",
            "amlang" to "en-gb"
        )

        val headers =
            mapOf("User-Agent" to "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.9; rv:50.0) Gecko/20100101 Firefox/50.0")

        val res = khttp.get(VOS_DATA_SOURCE_URL, params = params, headers = headers)
        return res.jsonObject
    }
}