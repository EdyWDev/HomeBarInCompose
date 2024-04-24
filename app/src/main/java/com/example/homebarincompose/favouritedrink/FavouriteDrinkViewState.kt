package com.example.homebarincompose.favouritedrink

import com.example.homebarincompose.favouritedrink.model.DrinkModel

data class FavouriteDrinkViewState (
    val drinkList: List<DrinkModel> = emptyList()
)