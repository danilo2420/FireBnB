package com.example.firebnb.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.firebnb.R
import com.example.firebnb.databinding.FragmentHomeBinding
import com.example.firebnb.model.Place
import com.example.firebnb.model.User
import com.example.firebnb.model.api.FirebnbRepository
import com.example.firebnb.session.Session
import com.example.firebnb.utils.showToast
import com.example.firebnb.view.homeRecyclerView.PlaceAdapter
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {
    var _binding: FragmentHomeBinding? = null
    val binding: FragmentHomeBinding
        get() = checkNotNull(_binding) {"You tried to access the HomeFragment binding at a wrong time"}

    lateinit var places: List<Place>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initializeBinding(inflater, container)
        loadPlaces()
        val user: User? = Session.user


        return binding.root
    }

    private fun initializeBinding(inflater: LayoutInflater, container: ViewGroup?) {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadPlaces() {
        lifecycleScope.launch {
            places = FirebnbRepository().getAllPlaces()
            initializeRecyclerView()
        }
    }

    // TODO: I should only get the places that the user does not own
    private fun initializeRecyclerView() {
        val adapter = PlaceAdapter(places) { holder ->
            showToast("You clicked on " + holder.place.name, requireContext())
        }
        binding.recyclerHome.adapter = adapter
    }

}