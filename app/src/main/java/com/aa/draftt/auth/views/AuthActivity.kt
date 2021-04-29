package com.aa.draftt.auth.views

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.aa.draftt.R
import com.aa.draftt.auth.viewmodels.AuthViewModel
import com.aa.draftt.views.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {

    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // shared view model
        viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)

        if (viewModel.firebaseUser.value != null) {
            // user logged in
            // Go on to Home Activity
            Timber.d("User logged in")
            val intent = Intent(this, HomeActivity::class.java)
            // These flags clear all activities on the stack
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
            finish()

        } else {
            // user not logged in
            // set Login navigation graph
            Timber.d("User NOT logged in")
            setContentView(R.layout.activity_auth_layout)

        }

    }
}