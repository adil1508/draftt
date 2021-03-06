package com.aa.draftt.views.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.aa.draftt.databinding.OnboardingFragmentLayoutBinding
import com.google.android.material.tabs.TabLayoutMediator

class OnboardingPagerFragment : Fragment() {

    lateinit var binding: OnboardingFragmentLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = OnboardingFragmentLayoutBinding.inflate(inflater, container, false)

        // setup viewpager2 adapter
        binding.onboardingViewPager2.adapter = OnboardingViewPagerAdapter(this)

        // Connect the tabs with the viewpager
        TabLayoutMediator(
            binding.bottomDotsTablayout, binding.onboardingViewPager2
        ) { _, _ -> }.attach()

        return binding.root
    }

    // marking the class as inner gives it access to outer class's shiz
    private inner class OnboardingViewPagerAdapter(fragment: Fragment) :
        FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int {
            return ONBOARDING_SLIDES
        }

        override fun createFragment(position: Int): Fragment {
            return OnboardingScreenFragment.newInstance(position = position)
        }
    }

    companion object {
        private const val ONBOARDING_SLIDES = 3
    }
}