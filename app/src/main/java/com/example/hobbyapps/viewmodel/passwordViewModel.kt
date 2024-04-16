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
import com.example.hobbyapps.model.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class passwordViewModel(application: Application, savedStateHandle: SavedStateHandle):
    AndroidViewModel(application) {
    val passLD = MutableLiveData<User?>()
    private val statusLD: MutableLiveData<String> = MutableLiveData()


    val TAG = "volleyTag"
    private var queue: RequestQueue? = null

    fun password(password:String, id:String) {
        queue = Volley.newRequestQueue(getApplication())
        val url = "http://10.0.2.2/HobbyApps/changePass.php?password=$password&iduser=$id"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            {
                val sType = object : TypeToken<User>() { }.type
                val result = Gson().fromJson<User>(it, sType)
                passLD.value = result as User
                Log.d("showvoley", it)
            },
            {
                Log.d("showvoley", it.toString())
            })
        stringRequest.tag = TAG
        queue?.add(stringRequest)
    }
}