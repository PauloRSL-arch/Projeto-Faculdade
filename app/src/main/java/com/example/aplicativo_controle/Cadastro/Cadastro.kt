package com.example.aplicativo_controle.Cadastro

import android.content.Context
import android.widget.Toast
import com.example.aplicativo_controle.DataBase.AppDataBase
import com.example.aplicativo_controle.DataBase.DAO.Itens_Dao
import com.example.aplicativo_controle.DataBase.Models.Itens
import com.example.aplicativo_controle.dataClassError.DataResult
import com.example.aplicativo_controle.databinding.ActivityScreenHomeBinding
import com.example.aplicativo_controle.databinding.ScreenCadastroBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class Cadastro {
    private lateinit var userData: Itens_Dao
    private lateinit var DataBase: AppDataBase



    suspend fun insertUserData(
        name_user: String,
        email_user : String,
        senha_user : String,
        cpf_user : String,
        context: Context
    ): DataResult{
        this.DataBase = AppDataBase.getInstance(context) as AppDataBase
        this.userData = DataBase.UserDao()

        return CoroutineScope(Dispatchers.IO).async{

            if(checkUserUsed(emailUser = email_user)) {
                return@async DataResult(false, "Email já cadastrado")
            }
            val saveData = save_users(
                name_user = name_user,
                email_user = email_user,
                senha_user = senha_user,
                cpf_user = cpf_user)
            if(saveData.success)
            {
                return@async DataResult(true, "Cadastro Efetuado com Sucesso")
            }else{
                return@async DataResult(false, "Dados invalidos")
            }
        }.await()
    }
    private suspend fun save_users(name_user: String, email_user : String, senha_user : String, cpf_user : String) : DataResult{
        if (email_user.isEmpty() || email_user.isBlank()&&
            senha_user.isEmpty()|| senha_user.isBlank() &&
            name_user.isEmpty() || name_user.isBlank() &&
            cpf_user.isEmpty() || cpf_user.isBlank()
        ){
            return DataResult(false)
        }
        userData.insert_users(
            Itens(
                nome = name_user,
                email = email_user,
                senha = senha_user,
                cpf = cpf_user)
        )
        return DataResult(true)
    }
    private suspend fun checkUserUsed(emailUser : String) : Boolean{
        val user = userData.findUserByEmail(idEmail = emailUser)
        if (user != null) {
            return true
        }
        return false
    }




}