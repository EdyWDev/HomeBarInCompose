package com.example.homebarincompose.service.model

import com.google.gson.annotations.SerializedName

data class DrinksDTO (
    @SerializedName("drinks")
    val drinks: List<DrinksModel>?
)