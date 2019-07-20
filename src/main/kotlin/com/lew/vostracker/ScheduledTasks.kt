package com.lew.vostracker

import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.concurrent.Executors.newScheduledThreadPool
import java.util.concurrent.TimeUnit


class ScheduledTasks(private val vosTrackerController: VoSTrackerController, private val vosService: VoSService) {
    fun schedule() {
        val scheduler = newScheduledThreadPool(1)

        val currentTime = LocalDateTime.now()
        val end = currentTime.plusHours(1).truncatedTo(ChronoUnit.HOURS)

        // Add 5 for a short delay after the hour starts.
        val delay = currentTime.until(end, ChronoUnit.SECONDS) + 60
        val period = 3600L

        // Run immediately, then on the next hour, then every hour after.
        val task = VoSRequesterTask(vosTrackerController, vosService)

        val thread = Thread(task)
        thread.isDaemon = true
        thread.start()

        scheduler.scheduleAtFixedRate(task, delay, period, TimeUnit.SECONDS)
    }
}