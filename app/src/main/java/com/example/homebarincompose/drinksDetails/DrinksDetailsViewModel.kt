package com.example.homebarincompose.drinksDetails

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homebarincompose.drinksDetails.ui.DrinksDetailsViewState
import com.example.homebarincompose.recipesearch.model.Drinks
import com.example.homebarincompose.service.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class DrinksDetailsViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository
) : ViewModel() {


    private val _viewState = MutableStateFlow(
        DrinksDetailsViewState(
            drinkList = emptyList(),
            idDrink = "",
            isFavourite = false
        )
    )

    val viewState = _viewState

    fun fetchDrinkDetails(drinkId: String){
        viewModelScope.launch {
            val drink = recipeRepository.getDrinkByID(drinkId)
            Log.d("DrinksDetailsViewModel", "fetched drink: $drink")
            if(drink != null){
                _viewState.update { currentState ->
                    currentState.copy(
                        drinkList = listOf(drink),
                        idDrink = drink.idDrink.toString(),
                        isFavourite = recipeRepository.isFavourite(drink.idDrink.toString())
                    )
                }
            } else{
                Log.d("DrinksDetailsViewModel", "No drink found with ID: $drinkId")
            }
        }
    }

    fun toggleFavourite(drink: Drinks){
        viewModelScope.launch {
            if(recipeRepository.isFavourite(drink.idDrink.toString())){
                recipeRepository.removeFavourite(drink.idDrink.toString())
            } else{
                recipeRepository.addFavourite(drink)
            }
            _viewState.update { currentState ->
                currentState.copy(isFavourite = recipeRepository.isFavourite(drink.idDrink.toString()))
            }
        }
    }
}