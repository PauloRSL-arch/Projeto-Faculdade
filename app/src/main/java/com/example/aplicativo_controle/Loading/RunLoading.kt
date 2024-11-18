package com.example.aplicativo_controle.Loading

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import com.example.aplicativo_controle.Cadastro.ScreenCadastro

 object RunLoading {

     fun ExeLoading(activity: Class<out Activity>, context: Context) {
        val intent = Intent(context, activity)
        SetIntent.instace.intent = intent
        val loadingIntent = Intent(context, Loading::class.java)
        context.startActivity(loadingIntent)
    }
}