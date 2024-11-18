package com.example.aplicativo_controle.Notifications

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.aplicativo_controle.R

object NotificationApp {

    val ID_REQUISICAO_READ_CONTACTS = 1


    @RequiresApi(Build.VERSION_CODES.O)
    fun buildNotificationMensagerAndSend(context: Context, Titulo: String, Texto: String){
        val channelID = createNotificationChannel(context)
        val notification = NotificationCompat.Builder(context, channelID)
            .setContentTitle(Titulo)
            .setContentText(Texto)
            .setSmallIcon(R.mipmap.ic_launcher)
            .build()

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(1 , notification)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(context: Context):String{
            val channelId = "1"
            val channelName = "Meu Canal de Notificação"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, channelName, importance)
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
            return channelId

        }

    fun validationCheckNotificationPost(context: Context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                ID_REQUISICAO_READ_CONTACTS
            )
        }

        }
    fun CheckAndBuildNotification(context: Context, Titulo: String, Texto: String ){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    android.Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                buildNotificationMensagerAndSend(
                    context,
                    Titulo,
                    Texto
                )
            }
        }
        }
    }


