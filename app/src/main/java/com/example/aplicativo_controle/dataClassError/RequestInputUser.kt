package com.example.aplicativo_controle.dataClassError

import android.graphics.Color
import com.example.aplicativo_controle.databinding.ScreenCadastroBinding

object RequestInputUser{

    fun checkRequestPassword(binding: ScreenCadastroBinding) : DataResult{
        val senhaCadastro = binding.senhaCadastro.text.toString()

        if (!senhaCadastro.contains(Regex("[^a-zA-Z0-9]"))){
            binding.checkCaracterEspecial.setTextColor(Color.RED)
        }else{
            binding.checkCaracterEspecial.setTextColor(Color.GREEN)
        }
        if (senhaCadastro.length >= 8){
            binding.check8Letras.setTextColor(Color.GREEN)
        }else{
            binding.check8Letras.setTextColor(Color.RED)
        }
        if(senhaCadastro.any { it.isUpperCase() }){
            binding.checkMaiuscula.setTextColor(Color.GREEN)
            }else{
                binding.checkMaiuscula.setTextColor(Color.RED)
            }
        if(binding.checkMaiuscula.currentTextColor == Color.GREEN &&
            binding.check8Letras.currentTextColor == Color.GREEN &&
            binding.checkCaracterEspecial.currentTextColor == Color.GREEN){
            return DataResult(true)
        }else{
            return DataResult(false)
        }


    }
    fun checkRequestEmail(binding: ScreenCadastroBinding) : DataResult{
        if (!binding.emailCadastro.text.contains(Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"))) {
            binding.emailCadastro.error = "Email invalido"
            return DataResult(false)
        } else{
            return DataResult(true)
        }
    }
}