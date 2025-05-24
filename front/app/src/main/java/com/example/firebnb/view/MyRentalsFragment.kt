package com.example.firebnb.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.firebnb.R
import com.example.firebnb.databinding.FragmentMyRentalsBinding
import com.example.firebnb.view.rentalFragments.RentalsViewPagerAdapter
import com.google.android.material.tabs.TabLayout.TabLayoutOnPageChangeListener
import com.google.android.material.tabs.TabLayoutMediator


class MyRentalsFragment : Fragment() {
    var _binding: FragmentMyRentalsBinding? = null
    val binding: FragmentMyRentalsBinding
        get() = checkNotNull(_binding) {"Trying to access MyRentalsFragment's binding at a wrong time"}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initializeBinding(inflater, container)
        initializeViewPager()
        configureTabLayout()

        return binding.root
    }

    private fun initializeBinding(inflater: LayoutInflater, container: ViewGroup?) {
        _binding = FragmentMyRentalsBinding.inflate(inflater, container, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initializeViewPager() {
        binding.viewPagerRentals.adapter = RentalsViewPagerAdapter(this)
    }

    private fun configureTabLayout() {
        TabLayoutMediator(binding.tabLayoutRentals, binding.viewPagerRentals){tab, position ->
            tab.text = when(position){
                    0 -> "To come"
                    1 -> "Current rentals"
                    2 -> "Past rentals"
                    else -> "Wrong position"
                }
        }.attach()
    }

}