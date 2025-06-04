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
import com.example.firebnb.MainActivity
import com.example.firebnb.databinding.FragmentHomeBinding
import com.example.firebnb.model.Place
import com.example.firebnb.model.User
import com.example.firebnb.model.api.FirebnbRepository
import com.example.firebnb.model.api.PlaceWithImage
import com.example.firebnb.model.api.responses.PlaceWithImageList
import com.example.firebnb.session.Session
import com.example.firebnb.utils.logError
import com.example.firebnb.utils.logMessage
import com.example.firebnb.utils.showToast
import com.example.firebnb.view.homeRecyclerView.PlaceAdapter
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {
    var _binding: FragmentHomeBinding? = null
    val binding: FragmentHomeBinding
        get() = checkNotNull(_binding) {"You tried to access the HomeFragment binding at a wrong time"}

    private var job: Job? = null

    lateinit var places: List<PlaceWithImage>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initializeBinding(inflater, container)
        turnProgressbarOff()
        loadPlaces()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        setBottomNavVisibility(true)
    }

    private fun initializeBinding(inflater: LayoutInflater, container: ViewGroup?) {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        job?.cancel()
    }

    private fun loadPlaces() {
        job = lifecycleScope.launch {
            turnProgressbarOn()
            try {
                val _user = Session.getNonNullUser()
                logMessage("USER: " + _user.toString())
                places = FirebnbRepository()
                    .getAllPlacesWithImage()
                    .filter { placeWithImg -> placeWithImg.place.owner_id != Session.getNonNullUser().id }
                    .toList()
                initializeRecyclerView()
            } catch (e: Exception) {
                logError(e)
            }
            turnProgressbarOff()
        }
    }

    private fun initializeRecyclerView() {
        if (!isAdded || _binding == null)
            return
        val adapter = PlaceAdapter(places) { holder ->
            navigateToPlaceDetail(holder.placeWithImage)
        }
        binding.recyclerHome.adapter = adapter
        binding.recyclerHome.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
            ) {
                outRect.bottom = 50 // bottom margin for each viewholder
            }
        })
    }

    fun navigateToPlaceDetail(placeWithImage: PlaceWithImage) {
        val navController = findNavController()
        val action = HomeFragmentDirections.actionHomeFragmentToPlaceDetailFragment(placeWithImage)
        setBottomNavVisibility(false)
        navController.navigate(action)
    }

    private fun turnProgressbarOn() {
        if (!isAdded || _binding == null)
            return
        binding.rootHome.visibility = View.GONE
        binding.progressbarHome.visibility = View.VISIBLE

    }

    private fun turnProgressbarOff() {
        if (!isAdded || _binding == null)
            return
        binding.rootHome.visibility = View.VISIBLE
        binding.progressbarHome.visibility = View.GONE
    }

    private fun setBottomNavVisibility(visible: Boolean) {
        val mainActivity = activity as MainActivity
        mainActivity.binding.menuBottomNav.visibility = if (visible) View.VISIBLE else View.GONE
    }

}