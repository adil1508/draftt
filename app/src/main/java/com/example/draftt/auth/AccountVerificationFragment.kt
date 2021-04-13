package com.example.draftt.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.draftt.databinding.FragmentAccountVerificationBinding

class AccountVerificationFragment: Fragment() {

    private lateinit var binding: FragmentAccountVerificationBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAccountVerificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = AccountVerificationFragment()
    }
}