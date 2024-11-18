package com.example.aplicativo_controle.dataClassError

import android.widget.EditText

object dataErrorPreenchimentoisEmpty{
    fun checkEditText(editText: EditText) : Boolean{
        var valid = true
        if (editText.text.toString().isEmpty()) {
            editText.error = "Campo obrigatório"
            return !valid
        }
        return valid
    }
}