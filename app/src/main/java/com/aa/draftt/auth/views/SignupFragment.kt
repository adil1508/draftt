package com.aa.draftt.auth.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.aa.draftt.R
import com.aa.draftt.Utils
import com.aa.draftt.auth.viewmodels.AuthViewModel
import com.aa.draftt.databinding.FragmentSignupBinding
import com.aa.draftt.views.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class SignupFragment : Fragment() {

    private lateinit var binding: FragmentSignupBinding
    private val viewModel: AuthViewModel by activityViewModels()    // shared viewmodel with activity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignupBinding.inflate(inflater, container, false)
        setupLiveDataObservers()
        setupListeners()
        return binding.root
    }

    private fun setupLiveDataObservers() {
        viewModel.authResult.observe(viewLifecycleOwner, { authResult ->
            when (authResult.status) {
                true -> {
                    Timber.d("Successfully signed up!")
                    binding.progressbar.visibility = View.GONE
                    writeUserEmailToSharedPref(viewModel.user.value?.email)
                    // TODO: Start another activity for authenticated users
                    // findNavController().navigate(SignupFragmentDirections.actionSignupFragmentToAccountVerificationFragment())
                }
                false -> {
                    Timber.d("Could not sign up")
                    binding.progressbar.visibility = View.GONE
                }
            }
        })

        viewModel.user.observe(viewLifecycleOwner, { user ->
            if (user != null) {
                Toast.makeText(
                    requireContext(),
                    "Signed up user with email: ${user.email}",
                    Toast.LENGTH_LONG
                ).show()
                writeUserEmailToSharedPref(viewModel.user.value?.email)
            } else {
                Toast.makeText(
                    requireContext(),
                    "Could not Sign up user",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    private fun writeUserEmailToSharedPref(email: String?) {
        val sharedPref = activity?.getSharedPreferences(
            getString(R.string.SHARED_PREF_FILE_KEY),
            Context.MODE_PRIVATE
        ) ?: return
        with(sharedPref.edit()) {
            putString(getString(R.string.SHARED_PREF_USER_EMAIL_KEY), email)
            apply()
        }
    }

    private fun setupListeners() {
        setupButtonListener()
        setupClickableSpans()
    }

    private fun setupButtonListener() {
        binding.signupButton.setOnClickListener {
            binding.progressbar.visibility = View.VISIBLE
            viewModel.signup(
                binding.emailInputLayout.editText?.text.toString(),
                binding.passwordInputLayout.editText?.text.toString()
            )
            Utils.hideKeyboard(requireContext(), binding.root)
        }
    }

    private fun setupClickableSpans() {
        val loginTextSpannable = SpannableString(binding.loginText.text)
        loginTextSpannable.setSpan(
            CustomClickableSpan(),
            binding.loginText.text.indexOf(getString(R.string.login_text)),
            binding.loginText.text.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.loginText.text = loginTextSpannable
        binding.loginText.movementMethod = LinkMovementMethod.getInstance()

    }

    inner class CustomClickableSpan : ClickableSpan() {

        override fun updateDrawState(ds: TextPaint) {
            super.updateDrawState(ds)
            ds.isUnderlineText = false
            ds.color = resources.getColor(R.color.orange_500, requireActivity().theme)
        }

        override fun onClick(widget: View) {
            Timber.d("Login string tapped")
            findNavController().navigate(SignupFragmentDirections.actionSignupFragmentToLoginFragment())
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = SignupFragment()
    }
}