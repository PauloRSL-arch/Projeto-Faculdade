package com.example.aplicativo_controle.DataBase.Models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Itens(
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0,
    @ColumnInfo(name = "nome") val nome : String,
    @ColumnInfo(name = "email") val email : String,
    @ColumnInfo(name = "senha") val senha : String,
    @ColumnInfo(name = "cpf") val cpf : String,
    @ColumnInfo(name = "Saldo") val saldo : Int? = 0
)
