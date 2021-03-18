package com.example.draftt.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.draftt.databinding.OnboardingFragmentLayoutBinding

class OnboardingPagerFragment : Fragment() {

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

        // setup viewpager2 adapter
        binding.onboardingViewPager2.adapter = OnboardingViewPagerAdapter(this)

        return binding.root
    }

    // TODO: implemented the FragmentStateAdapter here

    // marking the class as inner gives it access to outer class's shiz
    inner class OnboardingViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int {
            return ONBOARDING_SLIDES
        }

        override fun createFragment(position: Int): Fragment {
            TODO("Return a fragment based on this position")
        }

    }

}