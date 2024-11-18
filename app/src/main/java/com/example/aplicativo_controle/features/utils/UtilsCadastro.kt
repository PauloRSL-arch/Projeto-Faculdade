package com.example.aplicativo_controle.features.utils

import android.content.Context
import com.example.aplicativo_controle.DataBase.AppDataBase

object UtilsCadastro {
    suspend fun retornaIdUsuario(emailUser : String, context : Context) : Int{
        val DataBase = AppDataBase.getInstance(context) as AppDataBase
        val userData = DataBase.UserDao()
        val user = userData.findUserByEmail(emailUser)
        if (user != null){
            return user.id
        }else{
            return 0
        }

    }

}