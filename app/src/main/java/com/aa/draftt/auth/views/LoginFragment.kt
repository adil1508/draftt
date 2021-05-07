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
import androidx.navigation.fragment.findNavController
import com.aa.draftt.R
import com.aa.draftt.Utils
import com.aa.draftt.auth.viewmodels.AuthViewModel
import com.aa.draftt.databinding.FragmentLoginBinding
import com.aa.draftt.models.UserModel
import com.aa.draftt.views.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel: AuthViewModel by activityViewModels()    // gets the viewmodel shared by parent activity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        setupLiveDataObservers()
        setupListeners()
        return binding.root
    }

    private fun setupLiveDataObservers() {

        // only observe the navigate away to navigate away
        viewModel.navigateToAuthenticated.observe(viewLifecycleOwner, { navigate ->
            when (navigate) {
                true -> {
                    Timber.d("Navigating to Authenticated activity")
                    binding.progressbar.visibility = View.GONE

                    // before we are ready to navigate, write user shizzle to sharefPref
                    writeUserToSharedPref(viewModel.user.value)

                    val intent = Intent(requireContext(), HomeActivity::class.java)
                    // These flags clear all activities on the stack
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    requireActivity().finish()
                }
            }
        })

        viewModel.error.observe(viewLifecycleOwner, { error ->
            Toast.makeText(requireContext(), error, Toast.LENGTH_LONG).show()
            binding.progressbar.visibility = View.GONE
        })

    }

    private fun writeUserToSharedPref(user: UserModel?) {
        user?.let {
            val sharedPref = activity?.getSharedPreferences(
                getString(R.string.SHARED_PREF_FILE_KEY),
                Context.MODE_PRIVATE
            ) ?: return
            with(sharedPref.edit()) {
                putString(getString(R.string.SHARED_PREF_USER_EMAIL_KEY), user.email)
                putString(getString(R.string.SHARED_PREF_USER_NAME_KEY), user.name)
                putString(getString(R.string.SHARED_PREF_USER_ID_KEY), user.id)
                apply()
            }
        }
    }

    private fun setupListeners() {
        setupButtons()
        setupClickableSpans()
    }

    private fun setupButtons() {
        binding.loginButton.setOnClickListener {
            binding.progressbar.visibility = View.VISIBLE
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