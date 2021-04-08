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
import androidx.navigation.fragment.findNavController
import com.example.draftt.R
import com.example.draftt.databinding.FragmentSignupBinding
import timber.log.Timber

class SignupFragment : Fragment() {

    private lateinit var binding: FragmentSignupBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignupBinding.inflate(inflater, container, false)
        setupClickableSpans()
        return binding.root
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