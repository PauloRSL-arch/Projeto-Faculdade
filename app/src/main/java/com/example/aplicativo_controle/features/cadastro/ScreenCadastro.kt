package com.example.aplicativo_controle.features.cadastro


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.aplicativo_controle.DataBase.AppDataBase
import com.example.aplicativo_controle.DataBase.DAO.Itens_Dao
import com.example.aplicativo_controle.features.utils.EyeViewPassword
import com.example.aplicativo_controle.Activity_Loading.RunLoading
import com.example.aplicativo_controle.Login.DadosUsuario
import com.example.aplicativo_controle.MainActivityLogin
import com.example.aplicativo_controle.features.utils.ToastUtils
import com.example.aplicativo_controle.features.utils.HelperToolbar
import com.example.aplicativo_controle.dataClassError.RequestInputUser
import com.example.aplicativo_controle.databinding.ScreenCadastroBinding
import com.example.aplicativo_controle.features.utils.UtilsCadastro
import com.example.aplicativo_controle.features.validation.ValidacaoCPF
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ScreenCadastro : AppCompatActivity() {
    private lateinit var binding: ScreenCadastroBinding
    private lateinit var DataBase: AppDataBase
    private lateinit var userData: Itens_Dao
    private lateinit var ValidatorCadastro : Cadastro

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ScreenCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val DadosUsuario = DadosUsuario.instance
        this.DataBase = AppDataBase.getInstance(this) as AppDataBase
        this.userData = DataBase.UserDao()
        this.ValidatorCadastro = Cadastro()
        ToastUtils.init(this)
        EyeViewPassword.eyeOpen(binding.senhaCadastro)
        EyeViewPassword.eyeOpen(binding.confirmaSenha)

        HelperToolbar.CreateToolbar("Cadastro", binding.menuBarScreenCadastro,this)

        binding.buttonCadastrar.setOnClickListener{
            DadosUsuario.name = binding.nameCompleto.text.toString()
            DadosUsuario.email = binding.emailCadastro.text.toString()
            DadosUsuario.senha = binding.senhaCadastro.text.toString()
            DadosUsuario.cpf = binding.cpfUser.text.toString()

            binding.nameCompleto.error = if (DadosUsuario.name.isEmpty())"Insira seu nome!" else null
            binding.emailCadastro.error = if(DadosUsuario.email.isEmpty())"Insira seu email!" else null
            binding.senhaCadastro.error = if(DadosUsuario.senha.isEmpty())"Insira sua senha!" else null
            binding.cpfUser.error = if(DadosUsuario.cpf.isEmpty())"Insira seu CPF!" else null

            val checkCPF = ValidacaoCPF.validarCPF(binding.cpfUser.text.toString())
            val checkpassword = RequestInputUser.checkRequestPassword(binding)
            val checkEmail = RequestInputUser.checkRequestEmail(binding)
            val confirmPassword = DadosUsuario.getConfirmPassword(binding.confirmaSenha.text.toString())

            if (confirmPassword.success &&
                checkpassword.success &&
                checkEmail.success &&
                checkCPF.successCpf) {
                try {
                    CoroutineScope(Dispatchers.IO).launch {
                        val resultValidationCadastro = ValidatorCadastro.insertUserData(
                            name_user = DadosUsuario.name,
                            email_user = DadosUsuario.email,
                            senha_user = DadosUsuario.senha,
                            cpf_user = DadosUsuario.cpf,
                            context = this@ScreenCadastro
                        )
                        DadosUsuario.id =
                            UtilsCadastro.retornaIdUsuario(DadosUsuario.email, this@ScreenCadastro)
                        if (resultValidationCadastro.success) {
                            withContext(Dispatchers.Main) {
                                binding.nameCompleto.text.clear()
                                binding.emailCadastro.text.clear()
                                binding.senhaCadastro.text.clear()
                                binding.confirmaSenha.text.clear()
                                ToastUtils.showToast(resultValidationCadastro.menssage)
                                finish()
                                RunLoading.ExeLoading(MainActivityLogin::class.java,this@ScreenCadastro)
                            }
                        } else {
                            withContext(Dispatchers.Main) {
                                ToastUtils.showToast(resultValidationCadastro.menssage)
                            }
                        }
                    }
                } catch (e: Exception) {
                    ToastUtils.showToast("Erro ao acessar o banco")

                }
            }else{
                if(!checkCPF.successCpf) binding.cpfUser.error = checkCPF.menssageCpf
                if(!confirmPassword.success) binding.confirmaSenha.error = confirmPassword.menssage
            }

        }
    }


    }


