package com.example.hobbyapps.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.hobbyapps.model.Dogs
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ListViewModelDogs(application: Application): AndroidViewModel(application) {
    val dogsLD = MutableLiveData<ArrayList<Dogs>>()
    val dogsLoadErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()

    val TAG = "volleyTag"
    private var queue: RequestQueue? = null

    fun refresh() {
        loadingLD.value = true
        dogsLoadErrorLD.value = false
        queue = Volley.newRequestQueue(getApplication())
        val url = "http://10.0.2.2/HobbyApps/Dogs.php?dogs_list"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            {// kondisi sukses
                val sType = object : TypeToken<ArrayList<Dogs>>() { }.type
                val result = Gson().fromJson<ArrayList<Dogs>>(it, sType)
                dogsLD.value = result
                loadingLD.value = false

                Log.d("showvoley", result.toString())
            },
            {// kondisi gagal
                Log.d("showvoley", it.toString())
                dogsLoadErrorLD.value = false
                loadingLD.value = false
            })
        stringRequest.tag = TAG
        queue?.add(stringRequest)

    }
    override fun onCleared() {
        super.onCleared()
        queue?.cancelAll(TAG)
    }
}