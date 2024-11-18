package com.example.aplicativo_controle.Home.SaldoUsuario

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.example.aplicativo_controle.DataBase.AppDataBase
import com.example.aplicativo_controle.DataBase.DAO.Itens_Dao
import com.example.aplicativo_controle.Home.openDrawerSelector
import com.example.aplicativo_controle.Home.resultSelector
import com.example.aplicativo_controle.Login.DadosUsuario
import com.example.aplicativo_controle.Notifications.Toasts.ToastUtils
import com.example.aplicativo_controle.dataClassError.dataErrorPreenchimentoisEmpty
import com.example.aplicativo_controle.databinding.FragmentSaldoUsuarioBinding
import kotlinx.coroutines.launch

class HalfScreenSaldoUsuario : DialogFragment(){
    private var _binding : FragmentSaldoUsuarioBinding? = null
    private val binding get() = _binding!!
    private var actionType : String? = null
    private lateinit var DataBase: AppDataBase
    private lateinit var userData: Itens_Dao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            actionType = it.getString("ACTION_TYPE")
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


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSaldoUsuarioBinding.inflate(
            inflater,
            container,
            false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val context = requireContext()
        this.DataBase = AppDataBase.getInstance(context) as AppDataBase
        this.userData = DataBase.UserDao()

        when(actionType){
            "ADD_INFO_SALDO" -> viewLifecycleOwner.lifecycleScope.launch {setupAddInfoSaldo(context)}
        }
    }

    suspend private fun setupAddInfoSaldo(context: Context) {
        ToastUtils.init(context)

        binding.salarioAtual.visibility = View.VISIBLE
        binding.drawerselection.setOnClickListener {
                openDrawerSelector.show(context, binding.drawerselection)
        }

        binding.ButtonConfirmar.setOnClickListener {
            if(dataErrorPreenchimentoisEmpty.checkEditText(binding.salarioAtual)) {
                val saldo = binding.salarioAtual.text.toString()
                viewLifecycleOwner.lifecycleScope.launch {
                    userData.updateSaldo(DadosUsuario.instance.id, saldo.toInt())
                }
            }

        }

    }

    override fun onResume() {
        super.onResume()
    }


    companion object{
        fun newInstance(actionType : String) : HalfScreenSaldoUsuario{
            val fragment = HalfScreenSaldoUsuario()
            fragment.arguments = Bundle().apply {
                putString("ACTION_TYPE", actionType)
            }
            return fragment
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
