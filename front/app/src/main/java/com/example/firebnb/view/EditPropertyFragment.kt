package com.example.firebnb.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.firebnb.R
import com.example.firebnb.databinding.FragmentEditPropertyBinding
import com.example.firebnb.model.Place

class EditPropertyFragment : Fragment() {
    var _binding: FragmentEditPropertyBinding? = null
    val binding: FragmentEditPropertyBinding
        get() = checkNotNull(_binding) {"Trying to access null binding"}

    lateinit var place: Place

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initializeBinding(inflater, container)
        loadData() // we should have the place now

        return binding.root
    }

    private fun initializeBinding(inflater: LayoutInflater, container: ViewGroup?) {
        _binding = FragmentEditPropertyBinding.inflate(inflater, container, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun loadData() {
        this.place = EditPropertyFragmentArgs.fromBundle(requireArguments()).place
    }

}