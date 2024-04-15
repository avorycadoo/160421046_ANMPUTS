package com.example.hobbyapps.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.hobbyapps.R
import com.example.hobbyapps.model.Dogs
import com.example.hobbyapps.util.loadImage

class DogListAdapter(val dogsList:ArrayList<Dogs>)
    : RecyclerView.Adapter<DogListAdapter.DogsViewHolder>() {
    class DogsViewHolder(var view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.dogs_list_item, parent, false)
        return DogsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dogsList.size
    }

    override fun onBindViewHolder(holder: DogsViewHolder, position: Int) {
        val title = holder.view.findViewById<TextView>(R.id.txtTitle)
        val pembuat = holder.view.findViewById<TextView>(R.id.txtPembuat)
        val desc = holder.view.findViewById<TextView>(R.id.txtDescription)
        val btnRead = holder.view.findViewById<Button>(R.id.btnRead)

        title.text = dogsList[position].title
        pembuat.text = dogsList[position].username_pembuat
        desc.text = dogsList[position].description

        btnRead.setOnClickListener{
            val action =DogListFragmentDirections.actionDogsDetailFragment(dogsList[position].id.toString())
            Navigation.findNavController(it).navigate(action)

        }

        var imageViewDogs = holder.view.findViewById<ImageView>(R.id.imageViewDogs)
        var progressBarDogs = holder.view.findViewById<ProgressBar>(R.id.progressBarDogs)
        imageViewDogs.loadImage(dogsList[position].image, progressBarDogs)
    }

    fun updateDogsList(newDogsList: ArrayList<Dogs>) {
        dogsList.clear()
        dogsList.addAll(newDogsList)
        notifyDataSetChanged()
    }

}