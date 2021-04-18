package com.example.draftt

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment

// Global Helper Functions
class Utils {

    companion object {
        fun hideKeyboard(context: Context, rootView: View){
            val imm = context.getSystemService(InputMethodManager::class.java)
            imm.hideSoftInputFromWindow(rootView.windowToken, 0)
        }
    }

}