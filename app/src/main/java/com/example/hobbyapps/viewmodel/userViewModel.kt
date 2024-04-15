package com.example.hobbyapps.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.hobbyapps.model.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject

class userViewModel(application: Application, savedStateHandle: SavedStateHandle):
    AndroidViewModel(application)
{
    val userLD = MutableLiveData<User?>()
    private val statusLD: MutableLiveData<String> = MutableLiveData()


    val TAG = "volleyTag"
    private var queue: RequestQueue? = null

    fun login(username:String, password:String) {
        Log.d("username", username)
        queue = Volley.newRequestQueue(getApplication())
        val url = "http://10.0.2.2/HobbyApps/login.php?username=$username&password=$password"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            {
                val sType = object : TypeToken<User>() { }.type
                val result = Gson().fromJson<User>(it, sType)
                userLD.value = result as User
                Log.d("showvoley", it)
            },
            {
                Log.d("showvoley", it.toString())
            })
        stringRequest.tag = TAG
        queue?.add(stringRequest)
        }

    fun regist(username:String, password:String, firstname:String, lastname:String){
        queue = Volley.newRequestQueue(getApplication())
        val url = "http://10.0.2.2/HobbyApps/regis.php"

        val stringRequest = object : StringRequest(Method.POST, url,
            Response.Listener<String> { response ->
                // Handle response dari server
                Log.d("Response", response)
                val obj = JSONObject(response)
                if (obj.getString("result") == "OK") {
                    val data = obj.getJSONObject("data")
                    val sType = object : TypeToken<User>() {}.type
                    val result = Gson().fromJson<User>(data.toString(), sType)
                    val user1 = result as User

                    userLD.value = user1

                    Log.d("show_volley", result.toString())
                } else {
                    val msg = obj.getString("msg")
                    Log.d("error_volley", msg)
                    userLD.value = null
                }

            },

            Response.ErrorListener { error ->
                // Handle error dari server
                Log.e("Error", error.message ?: "Unknown error")
            }) {

            // Mengirim data pengguna sebagai bagian dari permintaan POST
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["username"] = username
                params["password"] = password
                params["firstname"] = firstname
                params["lastname"] = lastname
                return params
            }
        }

        // Menambahkan permintaan ke antrian Volley
        stringRequest.tag = TAG
        queue?.add(stringRequest)
    }

//    fun regist(username:String, password:String){
//        queue = Volley.newRequestQueue(getApplication())
//        val url = "http://10.0.2.2/HobbyApps/regis.php"
//
//        val stringRequest = StringRequest(
//            Request.Method.GET, url,
//            {
//                val sType = object : TypeToken<User>() { }.type
//                val result = Gson().fromJson<User>(it, sType)
//                userLD.value = result as User?
//                Log.d("showvoley", it)
//            },
//            {
//                Log.d("showvoley", it.toString())
//            })
//        stringRequest.tag = TAG
//        queue?.add(stringRequest)
//    }


    override fun onCleared() {
        super.onCleared()
        queue?.cancelAll(TAG)
    }
    }

