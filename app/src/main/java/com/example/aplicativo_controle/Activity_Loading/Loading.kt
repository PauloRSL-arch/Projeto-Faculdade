package com.example.aplicativo_controle.Activity_Loading

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.aplicativo_controle.R


class Loading : AppCompatActivity() {
    var TimeLoading : Long = 1300

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_loading2)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(SetIntent.instace.intent)
            finish()
        }, TimeLoading)
    }
}



    data class SetIntent(
        var intent: Intent
    ) {
        companion object {
            val instace: SetIntent by lazy { SetIntent(Intent()) }
        }
    }
