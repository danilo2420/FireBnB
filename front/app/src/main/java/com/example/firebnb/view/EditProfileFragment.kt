package com.example.firebnb.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.firebnb.R
import com.example.firebnb.databinding.FragmentEditProfileBinding

class EditProfileFragment : Fragment() {
    var _binding: FragmentEditProfileBinding? = null
    val binding: FragmentEditProfileBinding
        get() = checkNotNull(_binding) {"Trying to access wrong binding"}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initializeBinding(inflater, container)

        return binding.root
    }

    private fun initializeBinding(inflater: LayoutInflater, container: ViewGroup?) {
        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}