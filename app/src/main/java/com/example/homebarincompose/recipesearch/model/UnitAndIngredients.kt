package com.example.homebarincompose.recipesearch.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UnitAndIngredients (var unitIngredient: String, var ingredient: String): Parcelable {

    override fun toString() = "$unitIngredient $ingredient"
}