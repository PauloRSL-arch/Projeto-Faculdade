package com.example.aplicativo_controle

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.example.aplicativo_controle.Cadastro.ScreenCadastro
import com.example.aplicativo_controle.Home.ScreenHome
import com.example.aplicativo_controle.Loading.Loading
import com.example.aplicativo_controle.Loading.RunLoading
import com.example.aplicativo_controle.Login.Login
import com.example.aplicativo_controle.Notifications.NotificationApp
import com.example.aplicativo_controle.Notifications.Toasts.ToastUtils
import com.example.aplicativo_controle.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val DadosUsuario = com.example.aplicativo_controle.Login.DadosUsuario.instance
    private val _loginResult = MutableLiveData<Boolean>()
    val ID_REQUISICAO_READ_CONTACTS = 1

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Loading().TimeLoading = 2000
        ToastUtils.init(this)
        EyeViewPassword.eyeOpen(binding.EditSenha)
        viewClickButtonCadastro()
        viewClickButtonLogin()

    }


    fun viewClickButtonCadastro(){
        binding.CliqueCadastro.setOnClickListener{
            RunLoading.ExeLoading(ScreenCadastro::class.java, this)
        }
    }


    fun viewClickButtonLogin(){
        binding.ButtonEnviar.setOnClickListener {
            DadosUsuario.email = binding.EditEmail.text.toString()
            DadosUsuario.senha = binding.EditSenha.text.toString()

            if (DadosUsuario.email.isEmpty()) binding.EditEmail.error = "Preencha seu email!"
            if (DadosUsuario.senha.isEmpty()) binding.EditSenha.error = "Preencha sua senha!"

            CoroutineScope(Dispatchers.IO).launch{
                val loginSucess = Login.VerificaDadosLoginBanco(
                    context = this@MainActivity,
                    email = DadosUsuario.email,
                    senha = DadosUsuario.senha,
                    binding = binding
                )

                if (loginSucess.success){
                    finish()
                    RunLoading.ExeLoading(ScreenHome::class.java,this@MainActivity)
                    withContext(Dispatchers.Main) {
                        ToastUtils.showToast(loginSucess.menssage)
                    }
                }else{
                    withContext(Dispatchers.Main){
                        ToastUtils.showToast(loginSucess.menssage)
                    }
                }
            }
            NotificationApp.CheckAndBuildNotification(
                this,
                DadosUsuario.email + "Logado com sucesso",
                DadosUsuario.email
            )

        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == ID_REQUISICAO_READ_CONTACTS){
            if(grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                /*Permissao concedida*/
            }else {
                /*Permissao negada*/
            }
        }
    }


}