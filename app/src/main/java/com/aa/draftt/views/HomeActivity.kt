package com.aa.draftt.views

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.aa.draftt.R
import com.aa.draftt.databinding.ActivityHomeBinding

class HomeActivity: AppCompatActivity() {

    lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPref = getSharedPreferences(
            getString(R.string.SHARED_PREF_FILE_KEY),
            Context.MODE_PRIVATE
        ) ?: return

        val loggedUser = sharedPref.getString(getString(R.string.SHARED_PREF_USER_EMAIL_KEY), "")
        Toast.makeText(this, loggedUser, Toast.LENGTH_SHORT).show()
    }
}