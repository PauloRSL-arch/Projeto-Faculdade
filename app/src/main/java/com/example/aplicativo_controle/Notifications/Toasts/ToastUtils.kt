package com.example.aplicativo_controle.Notifications.Toasts

import android.content.Context
import android.util.Log
import android.widget.Toast
import java.lang.ref.WeakReference

object ToastUtils {
    private var weakContext: WeakReference<Context>? = null


    fun init(context: Context) {
        weakContext = WeakReference(context)
    }

    fun showToast(message: String? = null) {
        weakContext?.get()?.let {
            Toast.makeText(it, message, Toast.LENGTH_SHORT).show()
        } ?: run {
            // Handle the case where context is not initialized
            Log.e("ToastUtils", "Context is not initialized")
        }
    }
}