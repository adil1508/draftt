package com.aa.draftt.auth.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.draftt.databinding.FragmentTeamnameBinding

class TeamNameFragment : Fragment() {

    private lateinit var binding: FragmentTeamnameBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTeamnameBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = TeamNameFragment()
    }
}