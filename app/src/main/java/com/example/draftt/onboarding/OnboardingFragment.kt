package com.example.draftt.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.draftt.databinding.OnboardingFragmentLayoutBinding

class OnboardingFragment : Fragment() {

    lateinit var binding: OnboardingFragmentLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = OnboardingFragmentLayoutBinding.inflate(inflater, container, false)

        return binding.root
    }

    // TODO: Put the ViewPager2 as the layout for this fragment
    // TODO: implemented the FragmentStateAdapter here
    // TODO: Need to figure out how to get the slides going
}