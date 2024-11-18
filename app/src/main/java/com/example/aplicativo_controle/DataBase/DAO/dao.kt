package com.example.aplicativo_controle.DataBase.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.aplicativo_controle.DataBase.Models.Itens

@Dao
interface Itens_Dao {
    @Insert
    suspend fun insert_users(item : Itens)

    @Query("SELECT* FROM itens WHERE  Email = :idEmail AND Senha = :idsenha")
    suspend fun findUserByEmailAndPassaword(idEmail: String, idsenha : String) : Itens?

    @Query("UPDATE itens SET nome = :newName WHERE id = :userId")
    suspend fun updateName(userId : Int, newName: String)

    @Query("UPDATE itens SET senha = :password WHERE id = :userId")
    suspend fun updatePassword(userId : Int, password: String)

    @Query("UPDATE itens SET saldo = :saldo WHERE id = :userId")
    suspend fun updateSaldo(userId : Int, saldo: Int?)

    @Query("DELETE FROM itens WHERE id = :id")
    suspend fun DeleteUserByEmail(id: Int)

    @Query("SELECT* FROM itens WHERE  email = :idEmail")
    suspend fun findUserByEmail(idEmail: String) : Itens?

    @Query("SELECT* FROM itens WHERE  senha = :idSenha")
    suspend fun findUserByPassword(idSenha: String) : Itens?

    @Query("SELECT* FROM itens WHERE  nome = :idNome")
    suspend fun findUserByNome(idNome: String) : Itens?




}