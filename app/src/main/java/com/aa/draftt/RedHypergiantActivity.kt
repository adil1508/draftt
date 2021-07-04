package com.aa.draftt

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aa.draftt.views.auth.AuthActivity

class RedHypergiantActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*
        *   For now it just routes to the AuthActivity, which checks if a user is logged in or no
        *       - If logged in, route to HomeActivity
        *       - Else, setup the AuthNavGraph
        */

        Intent(this, AuthActivity::class.java).apply {
            intent.flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_MULTIPLE_TASK or Intent.FLAG_ACTIVITY_NO_ANIMATION
            startActivity(this)
        }
    }
}