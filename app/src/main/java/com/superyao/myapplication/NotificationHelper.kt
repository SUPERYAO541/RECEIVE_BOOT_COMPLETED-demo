package com.superyao.myapplication

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class NotificationHelper private constructor() {

    companion object {
        private const val CHANNEL_MISSION_REPORT = "REPORT"

        const val ID_MISSION_REPORT = 1

        val instance: NotificationHelper by lazy { NotificationHelper() }
    }

    private val nmCompat: NotificationManagerCompat by lazy {
        NotificationManagerCompat.from(app)
    }

    init {
        initChannels()
    }

    private fun initChannels() {
        if (Build.VERSION.SDK_INT >= 26) {
            nmCompat.createNotificationChannel(
                NotificationChannel(
                    CHANNEL_MISSION_REPORT,
                    "TEST-01",
                    NotificationManager.IMPORTANCE_LOW
                )
            )
        }
    }

    fun notify(message: String) {
        val notification = NotificationCompat.Builder(app, CHANNEL_MISSION_REPORT)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(app.getString(R.string.app_name))
            .setContentText(message)
            .setContentIntent(mainPendingIntent())
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_LOW) // only for api < 26
            .build()
        nmCompat.notify(ID_MISSION_REPORT, notification)
    }

    /*
    PendingIntent
     */

    private fun mainPendingIntent(): PendingIntent {
        return PendingIntent.getActivity(
            app,
            0,
            Intent(app, MainActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK),
            PendingIntent.FLAG_UPDATE_CURRENT
        )
    }
}