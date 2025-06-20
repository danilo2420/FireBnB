package com.example.firebnb.view

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.firebnb.MainActivity
import com.example.firebnb.R
import com.example.firebnb.databinding.FragmentMyRentals2Binding
import com.example.firebnb.model.api.FirebnbRepository
import com.example.firebnb.model.api.responses.RentingPreview
import com.example.firebnb.session.Session
import com.example.firebnb.utils.logError
import com.example.firebnb.utils.showToast
import com.example.firebnb.view.rentingsRecyclerView.RentingPreviewAdapter
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class MyRentals2Fragment : Fragment() {
    // This myRentals fragment will have all the rentals in just one recycler view
    var _binding: FragmentMyRentals2Binding? = null
    val binding: FragmentMyRentals2Binding
        get() = checkNotNull(_binding) {"Trying to access null binding"}

    private var job: Job? = null

    lateinit var previews: List<RentingPreview>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initializeBinding(inflater, container)
        turnProgressbarOff()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        loadRentingPreviews()
        setBottomNavVisibility(true)
    }

    private fun initializeBinding(inflater: LayoutInflater, container: ViewGroup?) {
        _binding = FragmentMyRentals2Binding.inflate(inflater, container, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        job?.cancel()
    }

    private fun loadRentingPreviews() {
        job = lifecycleScope.launch {
            turnProgressbarOn()
            try {
                previews = FirebnbRepository().getRentingPreviewList(checkNotNull(Session.getNonNullUser().id))
                initializeRecyclerView()
            } catch (e: Exception) {
                //showToast("There was an error", requireContext())
                logError(e)
            }
            turnProgressbarOff()
        }
    }

    private fun initializeRecyclerView() {
        if (!isAdded || _binding == null)
            return
        val adapter = RentingPreviewAdapter(this.previews) { holder ->
            //showToast("You chose " + holder.rentingPreview.name + "!", requireContext())
            val navController = findNavController()
            val action = MyRentals2FragmentDirections.actionMyRentals2FragmentToRentalDetailFragment(holder.rentingPreview.id)
            setBottomNavVisibility(false)
            navController.navigate(action)
        }
        binding.recyclerRentingPreviews.adapter = adapter
        binding.recyclerRentingPreviews.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
            ) {
                outRect.bottom = 50
            }
        })
    }

    private fun turnProgressbarOn() {
        if (!isAdded || _binding == null)
            return
        binding.rootRentals.visibility = View.GONE
        binding.progressbarMyRentals.visibility = View.VISIBLE
    }

    private fun turnProgressbarOff() {
        if (!isAdded || _binding == null)
            return
        binding.rootRentals.visibility = View.VISIBLE
        binding.progressbarMyRentals.visibility = View.GONE
    }

    private fun setBottomNavVisibility(visible: Boolean) {
        val mainActivity = activity as MainActivity
        mainActivity.binding.menuBottomNav.visibility = if (visible) View.VISIBLE else View.GONE
    }


}