package com.example.homebarincompose.welcome

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homebarincompose.recipesearch.model.Drinks
import com.example.homebarincompose.service.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository
) : ViewModel() {

    private val _viewState = MutableStateFlow(value = WelcomeViewState(null))
    val viewState = _viewState.asStateFlow()


    fun fetchRandomDrink(){
        viewModelScope.launch {
            val drink = recipeRepository.getRandomDrink()
            _viewState.update {
                Log.e("EEEE","DrinkId: ${drink?.idDrink}")
                it.copy(randomDrinks = drink)
            }
        }
    }

    fun onShowDetailsFromRandomClicked(drinkId: Int) {
        TODO("Not yet implemented")
    }

}