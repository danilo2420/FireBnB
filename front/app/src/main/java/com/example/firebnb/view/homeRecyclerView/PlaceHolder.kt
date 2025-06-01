package com.example.firebnb.view.homeRecyclerView

import androidx.recyclerview.widget.RecyclerView
import com.example.firebnb.databinding.ItemPlaceBinding
import com.example.firebnb.model.Place
import com.example.firebnb.model.PlaceImage
import com.example.firebnb.model.api.PlaceWithImage
import com.example.firebnb.utils.setImage

class PlaceHolder (val binding: ItemPlaceBinding) : RecyclerView.ViewHolder (binding.root) {

    //lateinit var place: Place
    lateinit var placeWithImage: PlaceWithImage

    fun linkPlace(_p: PlaceWithImage) {
        this.placeWithImage = _p

        val place: Place = this.placeWithImage.place

        binding.txtName.text = place.name
        binding.txtType.text = place.type
        binding.txtPrice.text = place.price_per_night.toString()

        val image: PlaceImage? = this.placeWithImage.image
        if (image != null) {
            binding.imageView2.setImage(image.img)
        }
    }

}