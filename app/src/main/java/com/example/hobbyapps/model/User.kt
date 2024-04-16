package com.example.hobbyapps.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("iduser")
    val id:Int?,
    val username:String?,
    val password:String?,
    val firstname:String?,
    val lastname:String?,
    val image:String?
)
