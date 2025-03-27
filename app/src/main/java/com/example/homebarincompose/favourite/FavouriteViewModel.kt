package com.example.homebarincompose.favourite

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homebarincompose.recipesearch.model.Drinks
import com.example.homebarincompose.service.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository
): ViewModel() {

    private val _favouriteDrinks = MutableStateFlow<List<Drinks>>(emptyList())
    val favouriteDrinks: StateFlow<List<Drinks>> = _favouriteDrinks

    init {
        loadFavouriteDrinks()
    }
    private fun loadFavouriteDrinks(){
        viewModelScope.launch {
            _favouriteDrinks.value = recipeRepository.getFavouriteDrinks()
        }
    }

    fun removeFromFavourites(drink: Drinks){
        viewModelScope.launch {
            recipeRepository.removeFavourite(drink.idDrink.toString())
            loadFavouriteDrinks()
        }
    }
    fun refreshFavouriteDrinks(){
        loadFavouriteDrinks()
    }
}