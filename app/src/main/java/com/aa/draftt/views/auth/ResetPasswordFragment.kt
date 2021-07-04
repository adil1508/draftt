package com.aa.draftt.views.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.aa.draftt.R
import com.aa.draftt.Utils
import com.aa.draftt.databinding.FragmentResetPasswordBinding
import com.aa.draftt.viewmodels.auth.AuthViewModel
import timber.log.Timber

class ResetPasswordFragment : Fragment() {

    private lateinit var binding: FragmentResetPasswordBinding

    private val viewModel: AuthViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.navigateToLogin.value = false
        binding = FragmentResetPasswordBinding.inflate(inflater, container, false)
        setupListeners()

        viewModel.navigateToLogin.observe(viewLifecycleOwner, { navigate ->
            if (navigate) {
                binding.progressbar.visibility = View.GONE
                Timber.d("Navigating to Login Fragment")
                findNavController().navigate(ResetPasswordFragmentDirections.actionForgotPasswordFragmentToLoginFragment())
            }
        })

        viewModel.error.observe(viewLifecycleOwner, { error ->
            Toast.makeText(requireContext(), error, Toast.LENGTH_LONG).show()
            binding.progressbar.visibility = View.GONE
        })

        return binding.root
    }

    private fun setupListeners() {
        binding.resetPasswordButton.setOnClickListener {
            binding.progressbar.visibility = View.VISIBLE
            val emailText = binding.emailInputLayout.editText?.text.toString()
            if (validate(emailText)) {
                viewModel.resetPassword(emailText)
                // TODO: Set a Toast message here maybe
                Utils.hideKeyboard(requireContext(), binding.root)
            }
        }
    }

    private fun validate(email: String): Boolean {
        if (email.isEmpty()) {
            binding.emailInputLayout.error = getString(R.string.login_email_validation_error_empty)
            return false
        }

        if (!Utils.isValidEmail(email)) {
            binding.emailInputLayout.error =
                getString(R.string.login_email_validation_error_invalid)
            return false
        }

        return true
    }


    companion object {
        @JvmStatic
        fun newInstance() =
            ResetPasswordFragment()
    }
}