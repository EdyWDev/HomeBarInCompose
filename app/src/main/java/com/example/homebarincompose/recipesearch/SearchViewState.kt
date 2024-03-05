package com.example.homebarincompose.recipesearch

import com.example.homebarincompose.recipesearch.model.Drinks
import com.example.homebarincompose.recipesearch.model.DrinksModel
import com.example.homebarincompose.recipesearch.model.Recipe
import com.example.homebarincompose.recipesearch.model.TypeOfSearchEnum

data class SearchViewState(
    val drinksList: List<Drinks> = emptyList(),
   // val typeOfSearch: List<TypeOfSearchEnum>,
   // val indexOfSelectedSearch: Int,
   // val indexOfSelectedGlass: Int,
   // val searchViewMessage: String,
   // var response: Recipe?,
  //  val spinnerGlassResponse: Recipe?
)