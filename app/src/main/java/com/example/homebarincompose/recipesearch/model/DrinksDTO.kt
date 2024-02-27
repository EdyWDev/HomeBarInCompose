package com.example.homebarincompose.recipesearch.model

import com.google.gson.annotations.SerializedName

data class DrinksDTO (
    @SerializedName("drinks")
    val drinks: List<DrinksModel>?
        )