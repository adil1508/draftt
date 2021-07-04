package com.aa.draftt.views.onboarding

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.aa.draftt.views.auth.AuthActivity
import com.aa.draftt.databinding.OnboardingSlide1Binding
import com.aa.draftt.databinding.OnboardingSlide2Binding
import com.aa.draftt.databinding.OnboardingSlide3Binding
import timber.log.Timber

class OnboardingScreenFragment : Fragment() {

    private val position by lazy { arguments!!.getInt(POSITION_KEY) }

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
                    findNavController().navigate(OnboardingPagerFragmentDirections.actionOnboardingPagerFragmentToLoginFragment())
                }
                binding.root
            }
            else -> super.onCreateView(inflater, container, savedInstanceState)
        }
    }

    private fun startLoginActivity() {
        Timber.d("Starting Login Activity")
        val intent = Intent(activity, AuthActivity::class.java)
        startActivity(intent)
    }

    companion object {
        private const val POSITION_KEY = "position"

        fun newInstance(position: Int) = OnboardingScreenFragment().apply {
            arguments = bundleOf(
                POSITION_KEY to position
            )
        }
    }
}