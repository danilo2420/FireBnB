package com.example.firebnb.view.homeRecyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.firebnb.databinding.ItemPlaceBinding
import com.example.firebnb.model.Place
import com.example.firebnb.model.api.responses.PlaceWithImage

class PlaceAdapter (
    //var places: List<Place>,
    var places: List<PlaceWithImage>,
    val lambda: (PlaceHolder) -> Unit
) : RecyclerView.Adapter<PlaceHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPlaceBinding.inflate(inflater, parent, false)
        return PlaceHolder(binding)
    }

    override fun getItemCount(): Int = places.size

    override fun onBindViewHolder(holder: PlaceHolder, position: Int) {
        val place = places.get(position)
        holder.linkPlace(place)
        holder.binding.root.setOnClickListener{
            lambda(holder)
        }
    }

    fun setPlaceList(p: List<PlaceWithImage>) {
        this.places = p
        notifyDataSetChanged()
    }

}