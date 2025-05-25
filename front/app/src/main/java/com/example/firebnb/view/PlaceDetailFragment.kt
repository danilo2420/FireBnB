package com.example.firebnb.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.firebnb.R
import com.example.firebnb.databinding.FragmentPlaceDetailBinding
import com.example.firebnb.model.Place


class PlaceDetailFragment : Fragment() {
    var _binding: FragmentPlaceDetailBinding? = null
    val binding: FragmentPlaceDetailBinding
        get() = checkNotNull(_binding) {"Trying to access PlaceDetailFragment binding when null"}

    lateinit var place: Place

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initializeBinding(inflater, container)
        loadPlace()
        showData()
        initializeEvents()

        return binding.root
    }

    private fun initializeBinding(inflater: LayoutInflater, container: ViewGroup?) {
        _binding = FragmentPlaceDetailBinding.inflate(inflater, container, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadPlace() {
        this.place = PlaceDetailFragmentArgs.fromBundle(requireArguments()).place
    }

    private fun showData() {
        binding.apply {
            txtPlaceDetailName.setText("Name: ${place.name}")
            txtPlaceDetailType.setText("Type: ${place.type}")
            txtPlaceDetailDescription.setText("Description: ${place.description}")
            txtPlaceDetailPrice.setText("Price: ${place.price_per_night.toString()}")
            txtPlaceDetailStars.setText("Stars: ${place.stars.toString()}")
        }
    }

    private fun initializeEvents() {
        binding.btnGoToRentPlace.setOnClickListener {
            val navController = findNavController()
            val action = PlaceDetailFragmentDirections.actionPlaceDetailFragmentToRentPlaceFragment(place)
            navController.navigate(action)
        }
    }

}