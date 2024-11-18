package com.example.aplicativo_controle.Home

import android.annotation.SuppressLint
import android.content.Context
import android.renderscript.ScriptGroup.Binding
import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.aplicativo_controle.Cadastro.UtilsCadastro
import com.example.aplicativo_controle.DataBase.AppDataBase
import com.example.aplicativo_controle.DataBase.DAO.Itens_Dao
import com.example.aplicativo_controle.Home.SaldoUsuario.HalfScreenSaldoUsuario
import com.example.aplicativo_controle.Login.DadosUsuario
import com.example.aplicativo_controle.Notifications.Toasts.ToastUtils
import com.example.aplicativo_controle.R
import com.google.android.material.bottomsheet.BottomSheetDialog

object openDrawerSelector {
    private lateinit var DataBase: AppDataBase
    private lateinit var userData: Itens_Dao

    @SuppressLint("ClickableViewAccessibility", "InflateParams")
    fun show(context: Context, editText: TextView){
        this.DataBase = AppDataBase.getInstance(context) as AppDataBase
        this.userData = DataBase.UserDao()


        ToastUtils.init(context)
        val bottomSheetDialog = BottomSheetDialog(context)
        val view = LayoutInflater.from(context).inflate(R.layout.buttom_sheet_selection, null)
        bottomSheetDialog.setContentView(view)

        view.findViewById<TextView>(R.id.option_one).setOnClickListener {
            ToastUtils.showToast("1 selecionado")
            resultSelector.result(2)

            editText.text = "1 Selecionado"
            bottomSheetDialog.dismiss()
        }

        view.findViewById<TextView>(R.id.option_two).setOnClickListener {
            ToastUtils.showToast("2 selecionado")
            resultSelector.result(2)
            editText.text = "2 Selecionado"

            bottomSheetDialog.dismiss()
        }
        bottomSheetDialog.show()
    }
}

object resultSelector{
    var quantidadeSelecionada : Int = 1

    fun result(qtd : Int){
        quantidadeSelecionada = qtd
    }
    fun getText() : Int{
        return quantidadeSelecionada
    }


    }
