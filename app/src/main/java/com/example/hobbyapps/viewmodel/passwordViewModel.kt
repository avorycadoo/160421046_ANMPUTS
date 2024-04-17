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
import org.json.JSONObject

class passwordViewModel(application: Application, savedStateHandle: SavedStateHandle):
    AndroidViewModel(application) {
    val passLD = MutableLiveData<String?>()
    val statusLD = MutableLiveData<String>()


    val TAG = "volleyTag"
    private var queue: RequestQueue? = null

    fun password(password:String, id:String) {
        queue = Volley.newRequestQueue(getApplication())
        val url = "http://10.0.2.2/HobbyApps/changePass.php?password=$password&iduser=$id"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            {
                var obj = JSONObject(it)
                statusLD.value = obj.getString("result")
                passLD.value = obj.getString("password")
                Log.d("password live data", passLD.value.toString())
                Log.d("status live data", statusLD.value.toString())

//                val sType = object : TypeToken<String>() { }.type
//                val result = Gson().fromJson<String>(it, sType)
//                statusLD.value = result
//                Log.d("showvoley", it)
            },
            {
                Log.d("showvoley", it.toString())
            })
        stringRequest.tag = TAG
        queue?.add(stringRequest)
    }
}