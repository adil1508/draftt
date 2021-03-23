package com.example.draftt.onboarding

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.draftt.auth.LoginActivity
import com.example.draftt.databinding.OnboardingSlide1Binding
import com.example.draftt.databinding.OnboardingSlide2Binding
import com.example.draftt.databinding.OnboardingSlide3Binding
import timber.log.Timber

class OnboardingScreenFragment(private val position: Int) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return when (position) {
            0 -> OnboardingSlide1Binding.inflate(inflater, container, false).root
            1 -> OnboardingSlide2Binding.inflate(inflater, container, false).root
            2 -> {
                val binding: OnboardingSlide3Binding =
                    OnboardingSlide3Binding.inflate(inflater, container, false)
                binding.letsDrafttButton.setOnClickListener {
                    startLoginActivity()
                }
                binding.root
            }
            else -> super.onCreateView(inflater, container, savedInstanceState)
        }
    }

    private fun startLoginActivity() {
        Timber.d("Starting Login Activity")
        val intent = Intent(activity, LoginActivity::class.java)
        startActivity(intent)
    }
}