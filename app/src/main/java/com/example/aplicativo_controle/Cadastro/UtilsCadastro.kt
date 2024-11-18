package com.example.aplicativo_controle.Cadastro

import android.content.Context
import android.content.Intent
import com.example.aplicativo_controle.DataBase.AppDataBase
import com.example.aplicativo_controle.DataBase.DAO.Itens_Dao
import com.example.aplicativo_controle.MainActivity

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