package com.example.aplicativo_controle.dataClassError

data class DataResult(
    val success : Boolean = false,
    val menssage : String? = null,
    val menssageCpf : String? = null,
    val successCpf : Boolean = false
)

data class ListaCompras(
    val id : Int,
    val nomeProduto : String,
    val quantidadeProduto : Int,
    val precoProduto : Double
)

