package com.example.draftt.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.draftt.databinding.OnboardingFragmentLayoutBinding

class OnboardingFragment : Fragment() {

    lateinit var binding: OnboardingFragmentLayoutBinding

    lateinit var viewPager: ViewPager2

    private val ONBOARDING_SLIDES = 3

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = OnboardingFragmentLayoutBinding.inflate(inflater, container, false)

        // setup viewpager2
        viewPager = binding.onboardingViewPager2

        return binding.root
    }

    // TODO: implemented the FragmentStateAdapter here
    // TODO: Need to figure out how to get the slides going
}