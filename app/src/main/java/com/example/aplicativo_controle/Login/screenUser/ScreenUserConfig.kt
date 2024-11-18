package com.example.aplicativo_controle.Login.screenUser

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.aplicativo_controle.Utils.HelperToolbar
import com.example.aplicativo_controle.databinding.ActivityScreenUserConfigBinding
import com.example.aplicativo_controle.halfScreen.DialogHelper
import com.example.aplicativo_controle.halfScreen.HalfScreenDialogFragment

class ScreenUserConfig : AppCompatActivity() {
    private lateinit var binding: ActivityScreenUserConfigBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScreenUserConfigBinding.inflate(layoutInflater)
        enableEdgeToEdge()

        setContentView(binding.root)
        HelperToolbar.CreateToolbar("Configuracão Usuario", binding.menuBarScreenConfig, this)

        binding.alterarNameUser.setOnClickListener {
            DialogHelper.openHalfScreenDialog(supportFragmentManager, HalfScreenDialogFragment.newInstance("CHANGE_NAME"))
        }
        binding.alterarSenha.setOnClickListener {
            DialogHelper.openHalfScreenDialog(supportFragmentManager, HalfScreenDialogFragment.newInstance("CHANGE_PASSWORD"))
        }

        binding.removerConta.setOnClickListener {
            DialogHelper.openHalfScreenDialog(supportFragmentManager, HalfScreenDialogFragment.newInstance("DELETE_ACCOUNT"))
        }

        binding.sairApp.setOnClickListener {
            DialogHelper.openHalfScreenDialog(supportFragmentManager, HalfScreenDialogFragment.newInstance("CONFIRMAR_SAIDA"))
        }

    }
//    private fun openHalfScreenDialog(actionType : String){
//        val dialogFragment = HalfScreenDialogFragment.newInstance(actionType)
//        dialogFragment.show(supportFragmentManager, "HalfScreenDialog")
//    }
}
