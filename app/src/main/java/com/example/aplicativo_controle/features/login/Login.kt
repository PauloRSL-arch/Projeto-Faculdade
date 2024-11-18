package com.example.aplicativo_controle.features.login

import android.content.Context
import com.example.aplicativo_controle.DataBase.AppDataBase
import com.example.aplicativo_controle.DataBase.DAO.Itens_Dao
import com.example.aplicativo_controle.dataClassError.DataResult
import com.example.aplicativo_controle.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

object Login {
    private lateinit var userData: Itens_Dao
    private lateinit var DataBase: AppDataBase
    var count: Int = 0


    suspend fun VerificaDadosLoginBanco(
        context: Context,
        email: String,
        senha: String,
        binding: ActivityMainBinding
    ): DataResult {
        DataBase = AppDataBase.getInstance(context) as AppDataBase
        userData = DataBase.UserDao()

        return CoroutineScope(Dispatchers.IO).async {
            val checkText = checkCadastro(
                email_user_login = email,
                senha_user_login = senha
            )
            if (!checkText) return@async DataResult(false, "Preencha todas as informacoes")

            withContext(Dispatchers.Main) {
                if (userData.findUserByEmail(idEmail = email) != null) {
                    if (userData.findUserByPassword(idSenha = senha) != null) {
                        binding.EditEmail.text.clear()
                        binding.EditSenha.text.clear()
                        return@withContext DataResult(true, "Login efetuado")
                    } else {
                        count++
                        if (count >= 3) {
                            val text = "Esqueci minha senha"
                            binding.esqueciSenha.text = text
                            binding.esqueciSenha.setOnClickListener {

                            }

                        }
                        binding.EditSenha.text.clear()
                        binding.EditSenha.error = "Senha incorreta!"
                        return@withContext DataResult(false,"Senha incorreta")
                        //Alterar senha
                    }
                } else {
                    binding.EditEmail.error = "Email incorreto"
                    return@withContext DataResult(false,"Email incorreto/inexistente")
                }

            }

        }.await()
    }

    private fun checkCadastro(email_user_login : String, senha_user_login : String) : Boolean{
        if(email_user_login.isEmpty() || email_user_login.isBlank() &&
            senha_user_login.isEmpty() || senha_user_login.isBlank()){
            return false
        }
        return true
    }

}