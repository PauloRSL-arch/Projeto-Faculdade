package com.example.aplicativo_controle.Login

import androidx.lifecycle.ViewModel
import com.example.aplicativo_controle.dataClassError.DataResult

class DadosUsuario public constructor(
    var id : Int,
    var name : String,
    var email : String,
    var senha: String,
    var cpf : String
){
    companion object{
        val instance : DadosUsuario by lazy {DadosUsuario(0,"", "", "", "")}
    }
    fun getConfirmPassword(confirmSenha: String? = null): DataResult{
        if (confirmSenha == this.senha){
            return DataResult(true)
        }else{
            return DataResult(false,"Senha incorreta")
        }
    }
}
