package com.example.aplicativo_controle.Utils

import android.app.Activity
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

object HelperToolbar {

    fun CreateToolbar(title : String, toolbar: Toolbar, activity: AppCompatActivity){
        activity.setSupportActionBar(toolbar)
        activity.supportActionBar?.apply {
            this.title = title
            setDisplayHomeAsUpEnabled(true)
        }
        toolbar.setNavigationOnClickListener{activity.finish()}
    }

}