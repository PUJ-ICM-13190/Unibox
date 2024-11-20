package com.co.unibox

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.util.concurrent.TimeUnit

class NotificationService(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    companion object {
        const val CHANNEL_ID = "offers_channel"
        const val CHANNEL_NAME = "Comida Ofertas"
        const val CHANNEL_DESCRIPTION = "Notificaciones de ofertas de comida"

        private val notificationBank = listOf(
            "🍔 50% en hamburguesas. ¡Solo por hoy!",
            "🍕 2x1 en pizzas. ¡Corre por tu oferta!",
            "🌮 ¡Tacos al 30%! 🌟 Deliciosos y económicos.",
            "🍩 Café gratis al comprar 2 donas. ☕",
            "🥗 20% en ensaladas saludables. 🌱",
            "🍟 Papas fritas ilimitadas con tu combo. 🍟🔥"
        )

        // Método estático para programar notificaciones
        fun scheduleNotificationService(context: Context) {
            val workRequest = PeriodicWorkRequestBuilder<NotificationService>(
                15, TimeUnit.MINUTES // El intervalo mínimo permitido por WorkManager es 15 minutos
            ).build()

            WorkManager.getInstance(context).enqueue(workRequest)
        }
    }

    override fun doWork(): Result {
        // Aquí se ejecuta el envío de notificaciones
        sendNotification()
        return Result.success()
    }

    private fun sendNotification() {
        createNotificationChannel()

        val notificationText = notificationBank.random()

        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationId = (System.currentTimeMillis() % 10000).toInt()

        val notification = android.app.Notification.Builder(applicationContext, CHANNEL_ID)
            .setContentTitle("¡Oferta de comida!")
            .setContentText(notificationText)
            .setSmallIcon(R.drawable.ic_notification)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(notificationId, notification)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = CHANNEL_DESCRIPTION
            }

            val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
