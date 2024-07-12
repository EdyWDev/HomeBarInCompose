package com.example.homebarincompose.recipesearch

import com.example.homebarincompose.recipesearch.model.Drinks
import com.example.homebarincompose.recipesearch.model.TypeOfSearchEnum

data class SearchViewState(
    val drinksList: List<Drinks> /*= emptyList()*/,
    val isSearching: Boolean,
    val searchText: String,
    //var selectedValue: String,
    val selectedTypeOfSearch: TypeOfSearchEnum,
    val switchStateForName: Boolean,
    val switchStateForIngredients: Boolean
   // val typeOfSearch: List<TypeOfSearchEnum>
   // val typeOfSearch: List<TypeOfSearchEnum>,
   // val indexOfSelectedSearch: Int,
   // val indexOfSelectedGlass: Int,
   // val searchViewMessage: String,
   // var response: Recipe?,
  //  val spinnerGlassResponse: Recipe?
)