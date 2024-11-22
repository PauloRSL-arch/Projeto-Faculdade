package com.example.aplicativo_controle

class IMC {
    fun calcularIMC(peso: Double, altura: Double): Double {
        return peso / (altura * altura)
    }
}