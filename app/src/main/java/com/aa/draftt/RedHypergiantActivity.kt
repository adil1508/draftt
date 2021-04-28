package com.aa.draftt

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class RedHypergiantActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*
        * TODO:
        *  - rename this activity to something like TrampolineActivity
        *  - This will check if the user has opened the app for the very first time
        *       - If so, setup the OnboardingNavigationGraph and set a bool in sharedpref that it's done
        *       - else, route to AuthActivity
        *  - AuthActivity will check if user is logged in or not
        *       - If so, route to HomeActivity
        *       - If not, setup the LoginNavigationGraph
        *
        * */
    }
}