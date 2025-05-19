package com.example.firebnb.view.homeRecyclerView

import androidx.recyclerview.widget.RecyclerView
import com.example.firebnb.databinding.ItemPlaceBinding
import com.example.firebnb.model.Place

class PlaceHolder (val binding: ItemPlaceBinding) : RecyclerView.ViewHolder (binding.root) {

    lateinit var place: Place

    fun setPlace(_p: Place) {
        this.place = _p

        binding.txtName.text = place.name
        binding.txtType.text = place.type
        binding.txtDescription.text = place.description
        binding.txtPrice.text = place.price_per_night.toString()
    }

}