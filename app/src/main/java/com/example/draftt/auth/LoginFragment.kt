package com.example.draftt.auth

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
import com.example.draftt.databinding.FragmentLoginBinding
import timber.log.Timber

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        setupClickableSpans()
        return binding.root
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
            binding.signupText.text.indexOf("Sign Up!"),
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
        }

        override fun onClick(widget: View) {
            when (clickableTextType) {
                ClickableTextType.FORGET_PASSWORD -> Timber.d("Forgot password string tapped")
                ClickableTextType.SIGN_UP -> Timber.d("Sign up string tapped")
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