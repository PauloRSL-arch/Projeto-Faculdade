package com.example.aplicativo_controle.halfScreen

import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.example.aplicativo_controle.DataBase.AppDataBase
import com.example.aplicativo_controle.features.utils.EyeViewPassword
import com.example.aplicativo_controle.Login.DadosUsuario
import com.example.aplicativo_controle.features.utils.ToastUtils
import com.example.aplicativo_controle.dataClassError.DataResult
import com.example.aplicativo_controle.databinding.FragmentRenameNameUserBinding
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.launch

class HalfScreenDialogFragment : DialogFragment() {
    private var _binding: FragmentRenameNameUserBinding? = null
    private val binding get() = _binding!!
    private var actionType : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            actionType = it.getString("ACTION_TYPE")
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentRenameNameUserBinding.inflate(
            inflater,
            container,
            false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val context = requireContext()

        when(actionType){
            "CHANGE_NAME" ->  viewLifecycleOwner.lifecycleScope.launch { setupChangeName(context) }
            "CHANGE_PASSWORD" -> viewLifecycleOwner.lifecycleScope.launch{setupChangePassword(context)}
            "DELETE_ACCOUNT" -> viewLifecycleOwner.lifecycleScope.launch {setupDeleteAccount(context)}
            "CONFIRMAR_SAIDA" -> setupConfirmarSaida()
        }


    }
    @OptIn(DelicateCoroutinesApi::class)
    suspend fun setupChangeName(context : Context) {
        val DataBase = AppDataBase.getInstance(context) as AppDataBase
        val userData = DataBase.UserDao()

        binding.novoNome.hint = "Digite seu novo nome"
        binding.novoNome.visibility = View.VISIBLE
        binding.novoNome.inputType = InputType.TYPE_CLASS_TEXT


        binding.confirmEmail.hint = "Confirme seu email"
        binding.confirmEmail.visibility = View.VISIBLE
        binding.confirmEmail.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS

        binding.SenhaNova.visibility = View.GONE

        binding.ConfirmarSaida.visibility = View.GONE


        binding.ButtonConfirmarRename.text = "Confirmar"
        binding.ButtonConfirmarRename.visibility = View.VISIBLE

        binding.ButtonConfirmarRename.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                if (checkEmail().success){
                    val idUser = userData.findUserByEmail(binding.confirmEmail.text.toString())
                    if (binding.novoNome.text.isNotEmpty()) {
                        userData.updateName(idUser!!.id, binding.novoNome.text.toString())
                        ToastUtils.showToast("Nome alterado")
                        dismiss()
                    } else {
                        binding.novoNome.error = "Digite um nome"
                    }
                }else{
                    binding.confirmEmail.error = checkEmail().menssage
                }
            }
        }
    }

    suspend fun setupChangePassword(context: Context) {
        val DataBase = AppDataBase.getInstance(context) as AppDataBase
        val userData = DataBase.UserDao()
        binding.novoNome.hint = "Digite sua senha atual"
        binding.novoNome.visibility = View.VISIBLE
        EyeViewPassword.eyeOpen(binding.novoNome)

        binding.SenhaNova.hint = "Digite sua senha nova"
        binding.SenhaNova.visibility = View.VISIBLE
        EyeViewPassword.eyeOpen(binding.SenhaNova)

        binding.confirmEmail.hint = "Confirme seu email"
        binding.confirmEmail.visibility = View.VISIBLE
        binding.confirmEmail.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS

        binding.ConfirmarSaida.visibility = View.GONE

        binding.ButtonConfirmarRename.text = "Confirmar"
        binding.ButtonConfirmarRename.visibility = View.VISIBLE

        binding.ButtonConfirmarRename.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                if(checkEmail().success){
                    val idUser = userData.findUserByEmail(binding.confirmEmail.text.toString())
                    if (binding.novoNome.text.toString() == idUser!!.senha) {
                        if (binding.SenhaNova.text.isNotEmpty() && binding.novoNome.text.isNotEmpty()) {
                            userData.updatePassword(idUser.id, binding.SenhaNova.text.toString())
                            ToastUtils.showToast("Senha alterada")
                            dismiss()
                        } else {
                            binding.SenhaNova.error = "Preencha a senha"
                        }
                    }else{
                        binding.novoNome.error = "Senha atual incorreta"
                    }
                }else{
                    binding.confirmEmail.error = checkEmail().menssage
                }
            }
        }

    }

    private fun setupDeleteAccount(context: Context) {
        val DataBase = AppDataBase.getInstance(context) as AppDataBase
        val userData = DataBase.UserDao()
        binding.confirmEmail.hint = "Confirme seu email"
        binding.confirmEmail.visibility = View.VISIBLE

        binding.SenhaNova.visibility = View.GONE
        binding.novoNome.visibility = View.GONE
        binding.ConfirmarSaida.visibility = View.GONE


        binding.ButtonConfirmarRename.text = "Remover conta"
        binding.ButtonConfirmarRename.visibility = View.VISIBLE

        binding.ButtonConfirmarRename.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch{
                if (checkEmail().success){
                    val idUser = userData.findUserByEmail(binding.confirmEmail.text.toString())
                    userData.DeleteUserByEmail(idUser!!.id)
                    ToastUtils.showToast("Conta Removida")
                    activity?.finishAffinity()
                }else{
                    binding.confirmEmail.error = checkEmail().menssage
                }
            }
        }
    }

    private fun setupConfirmarSaida() {
        binding.novoNome.visibility = View.GONE

        binding.SenhaNova.visibility = View.GONE

        binding.confirmEmail.visibility = View.GONE

        binding.ConfirmarSaida.text = "Voce tem certeza que deseja fechar o aplicativo?"
        binding.ConfirmarSaida.visibility = View.VISIBLE

        binding.ButtonConfirmarRename.text = "Sim"
        binding.ButtonConfirmarRename.visibility = View.VISIBLE

        binding.ButtonConfirmarRename.setOnClickListener {
            activity?.finishAffinity()
        }
    }
    private fun checkEmail() : DataResult{
        val confirmEmail = binding.confirmEmail.text.toString()
        if(binding.confirmEmail.text.isNotEmpty()) {
            if (confirmEmail == DadosUsuario.instance.email) {
                return DataResult(true,"Email encontrado")
            }else{
                return DataResult(false, "Email não encontrado")
            }
        }else{
            return DataResult(false, "Preencha seu email")
        }
    }



    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object{
        fun newInstance(actionType : String) : HalfScreenDialogFragment{
            val fragment = HalfScreenDialogFragment()
            fragment.arguments = Bundle().apply {
                putString("ACTION_TYPE", actionType)
            }
            return fragment
        }
    }
}

