package com.example.hobbyapps.model

import java.util.Date

data class Dogs(
    val id:Int?,
    val title:String?,
    val image:String?,
    val date:Date?,
    val description:String?,
    val isi: List<String>,
    val username_pembuat:String?
)


