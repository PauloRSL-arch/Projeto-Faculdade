package com.example.aplicativo_controle.halfScreen

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

object DialogHelper {
    fun openHalfScreenDialog(fragmentManager: FragmentManager, fragment: DialogFragment){
        val dialogFragment = fragment
        dialogFragment.show(fragmentManager, "HalfScreenDialog")
    }
}