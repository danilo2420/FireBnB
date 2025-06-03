package com.example.firebnb.view

import android.graphics.Rect
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.firebnb.databinding.FragmentMyPropertiesBinding
import com.example.firebnb.model.Place
import com.example.firebnb.model.api.FirebnbRepository
import com.example.firebnb.model.api.PlaceWithImage
import com.example.firebnb.session.Session
import com.example.firebnb.utils.showToast
import com.example.firebnb.view.homeRecyclerView.PlaceAdapter
import kotlinx.coroutines.launch


class MyPropertiesFragment : Fragment() {
    var _binding: FragmentMyPropertiesBinding? = null
    val binding: FragmentMyPropertiesBinding
        get() = checkNotNull(_binding) {"Trying to access MyPropertiesFragment's binding at a wrong time"}

    lateinit var places: List<PlaceWithImage>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initializeBinding(inflater, container)
        turnProgressbarOff()
        loadPlaces()
        initializeEvents()

        return binding.root
    }

    private fun initializeBinding(inflater: LayoutInflater, container: ViewGroup?) {
        _binding = FragmentMyPropertiesBinding.inflate(inflater, container, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initializeEvents() {
        binding.btnGoToCreateProperty.setOnClickListener {
            val navController = findNavController()
            val action = MyPropertiesFragmentDirections.actionMyPropertiesFragmentToCreatePropertyFragment()
            navController.navigate(action)
        }
    }

    private fun loadPlaces() {
        lifecycleScope.launch {
            turnProgressbarOn()
            try {
                places = FirebnbRepository()
                    .getAllPlacesWithImage()
                    .filter { placeWithImage -> placeWithImage.place.owner_id == Session.getNonNullUser().id }
                    .toList()
                initializeRecyclerView()
            } catch (e: Exception) {
                showToast("There was an error. Try again later", requireContext())
                e.printStackTrace()
            }
            turnProgressbarOff()
        }
    }

    private fun initializeRecyclerView() {
        val adapter = PlaceAdapter(places){ holder ->
            navigateToPropertyDetail(holder.placeWithImage.place)
        }
        binding.recyclerProperties.adapter = adapter
        binding.recyclerProperties.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
            ) {
                outRect.bottom = 50 // bottom margin for each viewholder
            }
        })
    }

    private fun navigateToPropertyDetail(place: Place) {
        val navController = findNavController()
        val action = MyPropertiesFragmentDirections
            .actionMyPropertiesFragmentToPropertyDetailFragment(place)
        navController.navigate(action)
    }

    private fun turnProgressbarOn() {
        binding.rootProperties.visibility = View.GONE
        binding.progressbarProperties.visibility = View.VISIBLE
    }

    private fun turnProgressbarOff() {
        binding.rootProperties.visibility = View.VISIBLE
        binding.progressbarProperties.visibility = View.GONE
    }

}