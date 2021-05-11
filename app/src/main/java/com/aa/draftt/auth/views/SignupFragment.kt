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
import com.aa.draftt.databinding.FragmentSignupBinding
import com.aa.draftt.models.UserModel
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

        viewModel.navigateToAuthenticated.observe(viewLifecycleOwner, { navigate ->
            when (navigate) {
                true -> {
                    Timber.d("Successfully signed up! and about to navigate away")
                    binding.progressbar.visibility = View.GONE

                    // before we are ready to navigate, write user shizzle to sharefPref
                    writeUserToSharedPref(viewModel.user.value)

                    startHomeActivity()

                }
            }
        })

        viewModel.error.observe(viewLifecycleOwner, { error ->
            Toast.makeText(requireContext(), error, Toast.LENGTH_LONG).show()
            binding.progressbar.visibility = View.GONE
        })

    }

    private fun startHomeActivity() {
        val intent = Intent(requireContext(), HomeActivity::class.java)
        // These flags clear all activities on the stack
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        requireActivity().finish()
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
        setupButtonListener()
        setupClickableSpans()
    }

    private fun setupButtonListener() {
        binding.signupButton.setOnClickListener {
            binding.progressbar.visibility = View.VISIBLE

            validateAndSignup()
            Utils.hideKeyboard(requireContext(), binding.root)
        }
    }

    private fun validateAndSignup() {

        val nameText = binding.nameInputLayout.editText?.text.toString()
        val emailText = binding.emailInputLayout.editText?.text.toString()
        val passwordText = binding.passwordInputLayout.editText?.text.toString()
        val confirmPasswordText = binding.confirmPasswordInputLayout.editText?.text.toString()

        val inputs = HashMap<String, String>()
        inputs["name"] = nameText
        inputs["email"] = emailText
        inputs["password"] = passwordText
        inputs["confirm_password"] = confirmPasswordText

        val validationErrors = getValidationErrors(inputs)

        // clear all errors
        binding.nameInputLayout.error = null
        binding.emailInputLayout.error = null
        binding.passwordInputLayout.error = null
        binding.confirmPasswordInputLayout.error = null

        if (validationErrors.isEmpty()) {
            // if no errors, try to login
            viewModel.signup(nameText, emailText, passwordText)
        } else {
            // set the errors
            validationErrors.forEach { (key, value) ->
                when (key) {
                    "name" -> {
                        binding.nameInputLayout.error = value
                    }
                    "email" -> {
                        binding.emailInputLayout.error = value
                    }
                    "password" -> {
                        binding.passwordInputLayout.error = value
                    }
                    "confirm_password" -> {
                        binding.confirmPasswordInputLayout.error = value
                    }
                }
            }
            // need to turn off the spinner
            binding.progressbar.visibility = View.GONE
        }
        Utils.hideKeyboard(requireContext(), binding.root)

    }

    private fun getValidationErrors(inputs: HashMap<String, String>): HashMap<String, String> {

        val validationErrors = HashMap<String, String>()

        inputs.forEach { (key, value) ->
            when (key) {
                "name" -> {
                    if (value.isEmpty() && !validationErrors.containsKey(key)) {
                        validationErrors[key] =
                            getString(R.string.signup_name_validation_error_empty)
                    }
                }
                "email" -> {
                    if (value.isEmpty() && !validationErrors.containsKey(key)) {
                        validationErrors[key] =
                            getString(R.string.signup_email_validation_error_empty)
                    }
                    if (!Utils.isValidEmail(value) && !validationErrors.containsKey(key)) {
                        validationErrors[key] =
                            getString(R.string.signup_email_validation_error_invalid)
                    }
                }
                "password" -> {
                    if (value.isEmpty() && !validationErrors.containsKey(key)) {
                        validationErrors[key] =
                            getString(R.string.signup_password_validation_error_empty)
                    }
                    // check for length of password
                    if (value.length < 8 && !validationErrors.containsKey(key)) {
                        validationErrors[key] =
                            getString(R.string.signup_password_validation_error_length)
                    }
                }
                "confirm_password" -> {
                    // just check if it matches inputs["password"]
                    if (value.isEmpty() && !validationErrors.containsKey(key)) {
                        validationErrors[key] =
                            getString(R.string.signup_confirmpassword_validation_error_empty)
                    }
                    if (value != inputs["password"] && !validationErrors.containsKey(key)) {
                        validationErrors[key] =
                            getString(R.string.signup_confirmpassword_validation_error_mismatch)
                    }
                }
            }
        }

        return validationErrors

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