package com.example.aplicativo_controle.Activity_Loading

import android.app.Activity
import android.content.Context
import android.content.Intent

object RunLoading {

     fun ExeLoading(activity: Class<out Activity>, context: Context) {
        val intent = Intent(context, activity)
        SetIntent.instace.intent = intent
        val loadingIntent = Intent(context, Loading::class.java)
        context.startActivity(loadingIntent)
    }
}