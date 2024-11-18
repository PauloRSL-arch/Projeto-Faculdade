package com.example.aplicativo_controle.DataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.aplicativo_controle.DataBase.DAO.Itens_Dao
import com.example.aplicativo_controle.DataBase.Models.Itens

@Database(entities = [Itens::class], version = 1)
abstract class AppDataBase : RoomDatabase(){

    abstract fun UserDao() : Itens_Dao

    companion object{
        private const val DATABASE_NAME: String = "Nome-Banco"

        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun getInstance(context: Context): Any =
            INSTANCE ?: synchronized(this){
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it}
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDataBase::class.java, DATABASE_NAME
            ).build()


    }
}