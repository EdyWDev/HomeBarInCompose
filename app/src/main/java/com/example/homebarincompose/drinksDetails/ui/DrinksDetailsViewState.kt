package com.example.homebarincompose.drinksDetails.ui

import com.example.homebarincompose.recipesearch.model.Drinks
import com.example.homebarincompose.recipesearch.model.UnitAndIngredients

data class DrinksDetailsViewState(
    val drinkList: List<Drinks> = emptyList(),
    val idDrink: String = "",
    val isFavourite: Boolean,
    val listOfIngredients: List<UnitAndIngredients> = emptyList()
)