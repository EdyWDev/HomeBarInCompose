package com.example.homebarincompose.drinksDetails.ui

import com.example.homebarincompose.recipesearch.model.Drinks

data class DrinksDetailsViewState (
    val drinkList: List<Drinks> = emptyList(),
    val idDrink: String = ""
)