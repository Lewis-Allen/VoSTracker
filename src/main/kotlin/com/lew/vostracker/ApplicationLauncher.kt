package com.lew.vostracker

import javafx.application.Application
import javafx.application.Platform
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage
import kotlin.system.exitProcess


class ApplicationLauncher : Application() {

    override fun start(primaryStage: Stage) {

        val loader = FXMLLoader(javaClass.classLoader.getResource("VOSTrackerView.fxml"))
        val root = loader.load<Parent>()
        val scene = Scene(root, 250.0, 150.0)

        primaryStage.title = "VOS Tracker"
        primaryStage.scene = scene
        primaryStage.sizeToScene()
        primaryStage.isResizable = false
        primaryStage.setOnCloseRequest {
            Platform.exit()
            exitProcess(0)
        }
        primaryStage.show()

        val voSService = VoSService()

        val controller = loader.getController<VoSTrackerController>()
        controller.vosService = voSService

        val scheduledTasks = ScheduledTasks(loader.getController(), voSService)
        scheduledTasks.schedule()

    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(ApplicationLauncher::class.java)
        }
    }
}