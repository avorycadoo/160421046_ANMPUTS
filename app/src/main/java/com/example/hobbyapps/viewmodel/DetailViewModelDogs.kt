package com.example.hobbyapps.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.hobbyapps.model.Dogs
import com.example.hobbyapps.view.DogDetailFragment
import com.example.hobbyapps.view.DogDetailFragmentArgs
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DetailViewModelDogs(application: Application, savedStateHandle: SavedStateHandle): AndroidViewModel(application) {
    val dogsLD = MutableLiveData<Dogs>()
    val TAG = "volleyTag"

    var id = DogDetailFragmentArgs.fromSavedStateHandle(savedStateHandle).id
    private var queue: RequestQueue? = null
    fun fetch() {
        queue = Volley.newRequestQueue(getApplication())
        val url = "http://10.0.2.2/HobbyApps/Dogs.php?id=${id}"

        val stringRequest = StringRequest(
            Request.Method.GET, url, {
                val sType = object : TypeToken<Dogs>() {}.type
                val result = Gson().fromJson<Dogs>(it, sType)
                val dogs1 = result as Dogs

                dogsLD.value = dogs1

                Log.d("show_volley", it)
            },
            {
                Log.e("show_volley", it.toString())
            }
        )
        stringRequest.tag = TAG
        queue?.add(stringRequest)

    }
}