package com.example.aplicativo_controle

class ClassificacaoIMC {
    fun obterClassificacao(imc: Double): String {
        return when {
            imc < 18.5 -> "Abaixo do peso"
            imc in 18.5..24.9 -> "Peso normal"
            imc in 25.0..29.9 -> "Sobrepeso"
            imc >= 30.0 -> "Obesidade"
            else -> "IMC inválido"
        }
    }
}