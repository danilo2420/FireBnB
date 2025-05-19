package com.example.firebnb.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.firebnb.R
import com.example.firebnb.databinding.FragmentHomeBinding
import com.example.firebnb.databinding.FragmentMyPropertiesBinding
import com.example.firebnb.databinding.FragmentPropertyDetailBinding
import com.example.firebnb.model.Place

class PropertyDetailFragment : Fragment() {
    var _binding: FragmentPropertyDetailBinding? = null
    val binding: FragmentPropertyDetailBinding
        get() = checkNotNull(_binding) {"Trying to access a null binding"}

    lateinit var place: Place

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initializeBinding(inflater, container)
        loadPlace()

        binding.textView2.text = "You chose " + this.place.name

        return binding.root
    }

    private fun initializeBinding(inflater: LayoutInflater, container: ViewGroup?) {
        _binding = FragmentPropertyDetailBinding.inflate(inflater, container, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadPlace() {
        this.place = PropertyDetailFragmentArgs.fromBundle(requireArguments()).place
    }

}