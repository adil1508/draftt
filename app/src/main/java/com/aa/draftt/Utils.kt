package com.aa.draftt

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

// Global Helper Functions
class Utils {

    companion object {
        fun hideKeyboard(context: Context, rootView: View) {
            val imm = context.getSystemService(InputMethodManager::class.java)
            imm.hideSoftInputFromWindow(rootView.windowToken, 0)
        }
    }

}