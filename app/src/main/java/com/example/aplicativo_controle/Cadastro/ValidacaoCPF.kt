package com.example.aplicativo_controle.Cadastro

import com.example.aplicativo_controle.dataClassError.DataResult

object ValidacaoCPF {

    fun validarCPF(cpf: String): DataResult {
        // Filtra apenas os dígitos do CPF
        val cpfDigits = cpf.filter { it.isDigit() }

        // Verifica se o CPF possui 11 dígitos e não são todos iguais
        if (cpfDigits.length != 11 || cpfDigits.all { it == cpfDigits[0] }) return DataResult(successCpf = false, menssageCpf = "Cpf invalido")

        // Função para calcular cada dígito verificador
        fun calcularDigito(peso: IntArray, cpfSub: String): Char {
            val soma = cpfSub.mapIndexed { index, c ->
                c.toString().toInt() * peso[index]
            }.sum()

            val resultado = 11 - (soma % 11)
            return if (resultado < 10) resultado.toString().first() else '0'
        }

        // Pesos para o cálculo dos dígitos verificadores
        val peso1 = intArrayOf(10, 9, 8, 7, 6, 5, 4, 3, 2)
        val peso2 = intArrayOf(11, 10, 9, 8, 7, 6, 5, 4, 3, 2)

        // Calcula o primeiro e segundo dígitos verificadores
        val digito1 = calcularDigito(peso1, cpfDigits.substring(0, 9))
        val digito2 = calcularDigito(peso2, cpfDigits.substring(0, 10))

        // Verifica se os dígitos calculados correspondem aos do CPF fornecido
        if (cpfDigits[9] == digito1 && cpfDigits[10] == digito2){
            return DataResult(successCpf = true)
        }
        return DataResult(successCpf = false, menssageCpf = "Cpf invalido")
    }
}
