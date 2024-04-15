package com.example.hobbyapps.view

import android.os.Binder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.hobbyapps.R
import com.example.hobbyapps.databinding.FragmentDogDetailBinding
import com.example.hobbyapps.util.loadImage
import com.example.hobbyapps.viewmodel.DetailViewModelDogs
import com.squareup.picasso.Picasso
import kotlinx.coroutines.newFixedThreadPoolContext


class DogDetailFragment : Fragment() {
    private lateinit var viewModel: DetailViewModelDogs
    private lateinit var binding: FragmentDogDetailBinding
    private var index = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDogDetailBinding.inflate(inflater, container, false)
        return binding.root    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(DetailViewModelDogs::class.java)
        viewModel.fetch()


        observeViewModel()
    }

    fun observeViewModel() {
        viewModel.dogsLD.observe(viewLifecycleOwner, Observer { dogs ->
            // Load image using Picasso
            Picasso.get().load(dogs.image).into(binding.imageViewDetailDogs)
            val progressLoad = view?.findViewById<ProgressBar>(R.id.progressBarDetailDogs)
            progressLoad?.visibility = View.GONE

            binding.txtDetailTitle.text = dogs.title
            binding.txtDetailPembuat.text = dogs.username_pembuat
            binding.txtDetailContent.text = dogs.isi[index]

            // button prev diaktifkan
            binding.btnPrev.isEnabled = index > 0

            // button next diaktifkan
            binding.btnNext.isEnabled = index < dogs.isi.size - 1

            // Button next click listener
            binding.btnNext.setOnClickListener {
                if (index < dogs.isi.size - 1) {
                    index++
                    binding.txtDetailContent.text = dogs.isi[index]
                }
                if(index == dogs.isi.size - 1 )
                {
                    binding.btnNext.isEnabled = false
                }
                binding.btnPrev.isEnabled = true

            }
            // Button prev click listener
            binding.btnPrev.setOnClickListener {
                if (index > 0) {
                    index--
                    binding.txtDetailContent.text = dogs.isi[index]
                    binding.btnNext.isEnabled = true
                }
                binding.btnPrev.isEnabled = index > 0

            }
        })
    }
}