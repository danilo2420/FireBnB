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
        showData()

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

    private fun showData() {
        binding.txtPropertyName.text = "Name: ${this.place.name}"
        binding.txtPropertyOwner.text = "Owner id: " + (if (place.owner_id != null) place.owner_id else "-")
        binding.txtPropertyType.text = "Type: ${this.place.type}"
        binding.txtPropertyDescription.text = "Description: ${this.place.description}"
        binding.txtPropertyPrice.text = "Price: ${this.place.price_per_night}"
        binding.txtPropertyStars.text = "Stars: ${this.place.stars}"

    }
}