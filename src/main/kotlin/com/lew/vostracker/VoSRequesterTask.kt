package com.lew.vostracker

import org.json.JSONObject
import java.time.ZoneOffset
import java.time.ZonedDateTime

class VoSRequesterTask(private val vosTrackerController: VoSTrackerController, private val vosService: VoSService) :
    Runnable {

    override fun run() {
        try {
            vosTrackerController.refresh.isDisable = true
            vosTrackerController.header.text = "Refreshing..."

            val res = vosService.requestCurrentVoices()
            val vos = parseVoicesResponse(res)

            //val voicePair = Pair(VoS.valueOf(voices[0].toUpperCase()), VoS.valueOf(voices[1].toUpperCase()))

            val vosPair = VoS.valueOf(vos[0].toUpperCase()) to VoS.valueOf(vos[1].toUpperCase())

            vosTrackerController.setMessage(ZonedDateTime.now(ZoneOffset.UTC))
            vosTrackerController.setVoS(vosPair)
            vosTrackerController.refresh.isDisable = false
        } catch (e: Exception) {
            println(e.message)

            // Wait then retry
            Thread.sleep(10000L)
            run()
        }
    }

    private fun parseVoicesResponse(res: JSONObject): List<String> =
        res.getJSONObject("query")
            .getJSONArray("allmessages")
            .getJSONObject(0)
            .getString("content")
            .split(",")
    }