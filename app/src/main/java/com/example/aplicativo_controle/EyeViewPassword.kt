package com.example.aplicativo_controle

import android.annotation.SuppressLint
import android.text.InputType
import android.view.MotionEvent
import android.widget.EditText
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBindings
import javax.net.ssl.SSLSessionBindingListener

object EyeViewPassword {
    var isPasswordVisible = false

    @SuppressLint("ClickableViewAccessibility")
    fun eyeOpen(editText: EditText){
        editText.setOnTouchListener { v, event ->
            val drawableEnd = 2
            val drawable = editText.compoundDrawablesRelative[drawableEnd]

            if (drawable != null){
                if (event.x >= (editText.width - editText.paddingRight - drawable.intrinsicWidth)){
                    if (event.action == MotionEvent.ACTION_UP){
                        if (isPasswordVisible){
                            editText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                            editText.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.baseline_remove_red_eye_24,0)
                        }else{
                            editText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                            editText.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.baseline_remove_red_eye_24,0)
                        }
                        isPasswordVisible  = !isPasswordVisible
                        editText.setSelection(editText.text.length)
                    }else{
                        true
                    }
                }else{
                    false
                }
            }else{
                false
            }
            false

        }

    }

}