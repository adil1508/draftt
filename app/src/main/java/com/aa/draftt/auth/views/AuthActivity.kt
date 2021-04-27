package com.aa.draftt.auth.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.aa.draftt.R
import com.aa.draftt.auth.viewmodels.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {

    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // shared view model
        viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)

        if (viewModel.user.value == null) {
            // user not logged in
            // set Login navigation graph
            Timber.d("User NOT logged in")
        } else {
            // user logged in
            // Go on to Home Activity
            Timber.d("User logged in")
        }

        setContentView(R.layout.activity_login_layout)

    }
}