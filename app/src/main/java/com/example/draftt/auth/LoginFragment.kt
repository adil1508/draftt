package com.example.draftt.auth

import android.os.Bundle
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.draftt.databinding.LoginFragmentLayoutBinding

class LoginFragment : Fragment() {

    private lateinit var binding: LoginFragmentLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LoginFragmentLayoutBinding.inflate(inflater, container, false)
        setupClickableSpans()
        return binding.root
    }

    private fun setupClickableSpans() {
        TODO("Implement")
    }

    inner class CustomClickableSpan(private val clickableTextType: ClickableTextType) :
        ClickableSpan() {

        override fun updateDrawState(ds: TextPaint) {
            super.updateDrawState(ds)
            ds.isUnderlineText = false
        }

        override fun onClick(widget: View) {
            when (clickableTextType) {
                ClickableTextType.FORGET_PASSWORD -> TODO("Go to Forget password fragment")
                ClickableTextType.SIGN_UP -> TODO("Go to Sign up fragment")
            }
        }
    }

    enum class ClickableTextType {
        FORGET_PASSWORD,
        SIGN_UP
    }
}