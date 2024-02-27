package com.example.homebarincompose.recipesearch.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RecipeSearchExtraData (
    val alcoholPresence: PresenceOfAlcoholCategory
        ): Parcelable