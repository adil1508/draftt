package com.example.draftt.auth.views

import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.draftt.R
import com.example.draftt.Utils
import com.example.draftt.auth.viewmodels.LoginViewModel
import com.example.draftt.databinding.FragmentLoginBinding
import timber.log.Timber

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        setupListeners()
        return binding.root
    }

    private fun setupListeners() {
        setupButtons()
        setupClickableSpans()
    }

    private fun setupButtons() {
        binding.loginButton.setOnClickListener {
            viewModel.login(
                binding.emailInputLayout.editText?.text.toString(),
                binding.passwordInputLayout.editText?.text.toString()
            )
            Utils.hideKeyboard(requireContext(), binding.root)
        }
    }

    private fun setupClickableSpans() {

        val forgotPasswordSpannable = SpannableString(binding.forgotPasswordText.text)
        forgotPasswordSpannable.setSpan(
            CustomClickableSpan(ClickableTextType.FORGET_PASSWORD),
            0,
            binding.forgotPasswordText.text.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.forgotPasswordText.text = forgotPasswordSpannable
        binding.forgotPasswordText.movementMethod = LinkMovementMethod.getInstance()

        val signUpSpannable = SpannableString(binding.signupText.text)
        signUpSpannable.setSpan(
            CustomClickableSpan(ClickableTextType.SIGN_UP),
            binding.signupText.text.indexOf(getString(R.string.sign_up_text)),
            binding.signupText.text.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.signupText.text = signUpSpannable
        binding.signupText.movementMethod = LinkMovementMethod.getInstance()

    }

    inner class CustomClickableSpan(private val clickableTextType: ClickableTextType) :
        ClickableSpan() {

        override fun updateDrawState(ds: TextPaint) {
            super.updateDrawState(ds)
            ds.isUnderlineText = false
            ds.color = resources.getColor(R.color.orange_500, requireActivity().theme)
        }

        override fun onClick(widget: View) {
            when (clickableTextType) {
                ClickableTextType.FORGET_PASSWORD -> {
                    Timber.d("Forgot password string tapped")
                    findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToForgotPasswordFragment())
                }
                ClickableTextType.SIGN_UP -> {
                    Timber.d("Sign up string tapped")
                    findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToSignupFragment())
                }
            }
        }
    }

    enum class ClickableTextType {
        FORGET_PASSWORD,
        SIGN_UP
    }

    companion object {
        @JvmStatic
        fun newInstance() = LoginFragment()
    }
}