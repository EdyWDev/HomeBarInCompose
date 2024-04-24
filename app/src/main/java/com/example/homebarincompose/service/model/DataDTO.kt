package com.example.homebarincompose.service.model

import com.google.gson.annotations.SerializedName

class DataDTO(

    @SerializedName("object")
    val data: DrinksDTO?
)