package com.aa.draftt.auth.views

import android.content.Context
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
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.aa.draftt.R
import com.aa.draftt.Utils
import com.aa.draftt.auth.viewmodels.SignUpViewModel
import com.aa.draftt.databinding.FragmentSignupBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class SignupFragment : Fragment() {

    private lateinit var binding: FragmentSignupBinding
    private lateinit var viewModel: SignUpViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignupBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)
        setupLiveDataObservers()
        setupListeners()
        return binding.root
    }

    private fun setupLiveDataObservers() {
        viewModel.authResult.observe(viewLifecycleOwner, { authResult ->
            when (authResult.status) {
                true -> {
                    Timber.d("Successfully signed up!")
                    Toast.makeText(
                        requireContext(),
                        "Successfully signed up user, ${authResult.user?.email}!",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    binding.progressbar.visibility = View.GONE
                    writeUserEmailToSharedPref(authResult.user?.email)
                    // TODO: Start another activity for authenticated users
                    // findNavController().navigate(SignupFragmentDirections.actionSignupFragmentToAccountVerificationFragment())
                }
                false -> {
                    Timber.d("Could not sign up")
                    Toast.makeText(requireContext(), "Could not sign up", Toast.LENGTH_SHORT).show()
                    binding.progressbar.visibility = View.GONE
                }
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