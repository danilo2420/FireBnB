package com.example.firebnb.view.rentalFragments

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class RentalsViewPagerAdapter (fragment: Fragment) : FragmentStateAdapter (fragment) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment =
        when(position){
            0 -> FutureRentalsFragment()
            1 -> CurrentRentalsFragment()
            2 -> PastRentalsFragment()
            else -> throw Exception("Problem with position in rental's view pager")
        }
}