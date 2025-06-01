package com.example.firebnb.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.firebnb.databinding.FragmentPlaceDetailBinding
import com.example.firebnb.model.api.PlaceWithImage


class PlaceDetailFragment : Fragment() {
    var _binding: FragmentPlaceDetailBinding? = null
    val binding: FragmentPlaceDetailBinding
        get() = checkNotNull(_binding) {"Trying to access PlaceDetailFragment binding when null"}

    lateinit var placeWithImage: PlaceWithImage

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
        this.placeWithImage = PlaceDetailFragmentArgs.fromBundle(requireArguments()).placeWithImage
    }

    private fun showData() {
        binding.apply {
            txtPlaceDetailName.setText("Name: ${placeWithImage.place.name}")
            txtPlaceDetailType.setText("Type: ${placeWithImage.place.type}")
            txtPlaceDetailDescription.setText("Description: ${placeWithImage.place.description}")
            txtPlaceDetailPrice.setText("Price: ${placeWithImage.place.price_per_night.toString()}")
            txtPlaceDetailStars.setText("Stars: ${placeWithImage.place.stars.toString()}")
        }
    }

    private fun initializeEvents() {
        binding.btnGoToRentPlace.setOnClickListener {
            val navController = findNavController()
            val action = PlaceDetailFragmentDirections.actionPlaceDetailFragmentToRentPlaceFragment(placeWithImage.place)
            navController.navigate(action)
        }
    }

}