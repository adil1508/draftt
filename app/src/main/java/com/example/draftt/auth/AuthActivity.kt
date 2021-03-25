package com.example.draftt.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.draftt.R
import com.example.draftt.databinding.ActivityLoginLayoutBinding

class AuthActivity: AppCompatActivity() {

    lateinit var binding: ActivityLoginLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginLayoutBinding.inflate(layoutInflater, null, false)
        setContentView(binding.root)
    }
}