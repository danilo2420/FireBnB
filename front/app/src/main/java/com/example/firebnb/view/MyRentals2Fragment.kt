package com.example.firebnb.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.firebnb.R
import com.example.firebnb.databinding.FragmentMyRentals2Binding
import com.example.firebnb.model.api.FirebnbRepository
import com.example.firebnb.model.api.responses.RentingPreview
import com.example.firebnb.session.Session
import com.example.firebnb.utils.logError
import com.example.firebnb.utils.showToast
import com.example.firebnb.view.rentingsRecyclerView.RentingPreviewAdapter
import kotlinx.coroutines.launch


class MyRentals2Fragment : Fragment() {
    // This myRentals fragment will have all the rentals in just one recycler view
    var _binding: FragmentMyRentals2Binding? = null
    val binding: FragmentMyRentals2Binding
        get() = checkNotNull(_binding) {"Trying to access null binding"}

    lateinit var previews: List<RentingPreview>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initializeBinding(inflater, container)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        loadRentingPreviews()
    }

    private fun initializeBinding(inflater: LayoutInflater, container: ViewGroup?) {
        _binding = FragmentMyRentals2Binding.inflate(inflater, container, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadRentingPreviews() {
        lifecycleScope.launch {
            try {
                previews = FirebnbRepository().getRentingPreviewList(checkNotNull(Session.getNonNullUser().id))
                previews.forEach { preview -> Log.d("myMessage", preview.toString()) }
                initializeRecyclerView()
            } catch (e: Exception) {
                showToast("There was an error", requireContext())
                logError(e)
            }
        }
    }

    private fun initializeRecyclerView() {
        val adapter = RentingPreviewAdapter(this.previews) { holder ->
            //showToast("You chose " + holder.rentingPreview.name + "!", requireContext())
            val navController = findNavController()
            val action = MyRentals2FragmentDirections.actionMyRentals2FragmentToRentalDetailFragment(holder.rentingPreview.id)
            navController.navigate(action)
        }
        binding.recyclerRentingPreviews.adapter = adapter
    }

}