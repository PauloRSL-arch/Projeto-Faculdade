package com.example.aplicativo_controle

import com.example.aplicativo_controle.models.Itens

class DataSource {
    companion object{
        fun createDataSet(
            idProduto : String,
            Quant: Int,
            IdCartao: String,
            Valor: Double
        ): ArrayList<Itens>{
            val list = ArrayList<Itens>()
            list.add(
                Itens(
                    NomeProduto = idProduto,
                    Quantidade = Quant,
                    Cartao = IdCartao,
                    Valor = Valor
                )
            )

            return list
        }
    }
}