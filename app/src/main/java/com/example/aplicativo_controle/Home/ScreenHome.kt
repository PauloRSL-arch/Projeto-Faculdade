package com.example.aplicativo_controle.Home

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.aplicativo_controle.DataBase.AppDataBase
import com.example.aplicativo_controle.Activity_Loading.Loading
import com.example.aplicativo_controle.Activity_Loading.RunLoading
import com.example.aplicativo_controle.Login.DadosUsuario
import com.example.aplicativo_controle.features.ActivityConfigUser.ScreenUserConfig
import com.example.aplicativo_controle.features.utils.HelperToolbar
import com.example.aplicativo_controle.databinding.ActivityScreenHomeBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ScreenHome : AppCompatActivity() {
    private lateinit var binding: ActivityScreenHomeBinding
    private lateinit var  swipe : SwipeRefreshLayout

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        val TimeLoding = Loading()

        TimeLoding.TimeLoading = 500

        super.onCreate(savedInstanceState)
        this.binding = ActivityScreenHomeBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(this.binding.root)
        HelperToolbar.CreateToolbar("Inicio", binding.menuBar,this)
    }

    @SuppressLint("ResourceType")
    override fun onResume() {
        super.onResume()
        viewDadosUsuario()

        binding.swipeRefresh.setOnRefreshListener{
            viewDadosUsuario()
            binding.swipeRefresh.isRefreshing = false
        }
        HelperToolbar.CreateToolbar("Inicio", binding.menuBar,this)
    }

    override fun onStart() {
        super.onStart()
        viewDadosUsuario()
        clickInfoUser(this) //binding para abrir ao clicar no nome
    }

    fun viewDadosUsuario(){
        val DataBase = AppDataBase.getInstance(this) as AppDataBase
        val userData = DataBase.UserDao()
        CoroutineScope(Dispatchers.IO).launch {
            val userInfor = userData.findUserByEmail(DadosUsuario.instance.email)
            withContext(Dispatchers.Main){

                DadosUsuario.instance.email = userInfor!!.email
                DadosUsuario.instance.name = userInfor.nome
                DadosUsuario.instance.senha = userInfor.senha
                DadosUsuario.instance.cpf = userInfor.cpf
                DadosUsuario.instance.id = userInfor.id

                val nomeUser = "Nome: " + userInfor.nome
                val EmailUser = "Email: " + userInfor.email
                val CpfUser = "CPF: " + userInfor.cpf
                val saldoPrevisto = "Saldo Previsto: R$" + userInfor.saldo

                binding.nameUser.text = EmailUser
                binding.userEmail.text = nomeUser
                binding.cpfUser.text = CpfUser
            }
        }

    }
    fun clickInfoUser(context: Context){
        binding.ImageUser.setOnClickListener {
            RunLoading.ExeLoading(ScreenUserConfig::class.java, context)
        }
        binding.nameUser.setOnClickListener {
            RunLoading.ExeLoading(ScreenUserConfig::class.java, context)
        }
        binding.userEmail.setOnClickListener {
            RunLoading.ExeLoading(ScreenUserConfig::class.java, context)
        }
    }
}

