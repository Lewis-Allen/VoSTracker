package com.lew.vostracker

import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.text.Text
import java.nio.file.Paths
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

private val AMLODD_IMAGE = Image("/voices/AMLODD.png")
private val CADARN_IMAGE = Image("/voices/CADARN.png")
private val CRWYS_IMAGE = Image("/voices/CRWYS.png")
private val HEFIN_IMAGE = Image("/voices/HEFIN.png")
private val IORWERTH_IMAGE = Image("/voices/IORWERTH.png")
private val ITHELL_IMAGE = Image("/voices/ITHELL.png")
private val MEILYR_IMAGE = Image("/voices/MEILYR.png")
private val TRAHAEARN_IMAGE = Image("/voices/TRAHAEARN.png")
private val EMPTY_IMAGE = Image("/voices/UNDEFINED.png")

class VoSTrackerController {
    private val model = VoSTrackerModel()

    lateinit var vosService: VoSService

    @FXML
    lateinit var header: Text

    @FXML
    lateinit var vosOne: ImageView

    @FXML
    lateinit var vosTwo: ImageView

    @FXML
    lateinit var refresh: Button


    @FXML
    fun initialize() {
        vosOne.image = EMPTY_IMAGE
        vosTwo.image = EMPTY_IMAGE
    }

    fun setMessage(currentGameTime: ZonedDateTime) {
        val displayFormatter = DateTimeFormatter.ofPattern("HH:mm")

        val start = currentGameTime.truncatedTo(ChronoUnit.HOURS).format(displayFormatter)

        // Get xx:59 of current hour
        val end = currentGameTime.plusHours(1)
            .truncatedTo(ChronoUnit.HOURS)
            .plusMinutes(-1)
            .format(displayFormatter)

        header.text = "The current VoS of Seren \n($start to $end game time) is:"
    }

    fun setVoS(vos: Pair<VoS, VoS>) {
        (Paths.get("").toAbsolutePath().toString())
        model.currentVoS = vos

        vosOne.image = getImageForVoS(model.currentVoS.first)
        vosTwo.image = getImageForVoS(model.currentVoS.second)
    }

    private fun getImageForVoS(vos: VoS): Image {
        return when (vos) {
            VoS.AMLODD -> AMLODD_IMAGE
            VoS.CADARN -> CADARN_IMAGE
            VoS.CRWYS -> CRWYS_IMAGE
            VoS.HEFIN -> HEFIN_IMAGE
            VoS.IORWERTH -> IORWERTH_IMAGE
            VoS.ITHELL -> ITHELL_IMAGE
            VoS.MEILYR -> MEILYR_IMAGE
            VoS.TRAHAEARN -> TRAHAEARN_IMAGE
            else -> EMPTY_IMAGE
        }
    }

    fun refresh() {
        header.text = "Refreshing..."
        Thread(VoSRequesterTask(this, vosService)).start()
    }
}