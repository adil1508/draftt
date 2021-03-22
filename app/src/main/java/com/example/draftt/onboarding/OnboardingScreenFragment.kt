package com.example.draftt.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.draftt.databinding.OnboardingSlide1Binding
import com.example.draftt.databinding.OnboardingSlide2Binding
import com.example.draftt.databinding.OnboardingSlide3Binding
import timber.log.Timber

class OnboardingScreenFragment(val position: Int) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return when (position) {
            0 -> OnboardingSlide1Binding.inflate(inflater, container, false).root
            1 -> OnboardingSlide2Binding.inflate(inflater, container, false).root
            2 -> OnboardingSlide3Binding.inflate(inflater, container, false).root
            else -> super.onCreateView(inflater, container, savedInstanceState)
        }
    }
}