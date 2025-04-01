package com.example.homebarincompose.drinksDetails

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homebarincompose.drinksDetails.ui.DrinksDetailsViewState
import com.example.homebarincompose.recipesearch.model.Drinks
import com.example.homebarincompose.recipesearch.model.UnitAndIngredients
import com.example.homebarincompose.service.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DrinksDetailsViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository
) : ViewModel() {


    private val _viewState = MutableStateFlow(
        DrinksDetailsViewState(
            drinkList = emptyList(),
            idDrink = "",
            isFavourite = false,
            listOfIngredients = emptyList()
        )
    )

    val viewState = _viewState

    fun fetchDrinkDetails(drinkId: String) {
        viewModelScope.launch {
            val drink = recipeRepository.getDrinkByID(drinkId)
            Log.d("DrinksDetailsViewModel", "fetched drink: $drink")

            if (drink != null) {
                val ingredientsList = drink.mapToIngredientList()
                _viewState.update { currentState ->
                    currentState.copy(
                        drinkList = listOf(drink),
                        idDrink = drink.idDrink.toString(),
                        isFavourite = recipeRepository.isFavourite(drink.idDrink.toString()),
                        listOfIngredients = ingredientsList
                    )
                }
                Log.d(
                    "DrinksDetailsViewModel",
                    "Updated state with ingredients: ${drink.mapToIngredientList()}"
                )
            } else {
                Log.d("DrinksDetailsViewModel", "No drink found with ID: $drinkId")
            }
        }
    }

    fun toggleFavourite(drink: Drinks) {
        viewModelScope.launch {
            if (recipeRepository.isFavourite(drink.idDrink.toString())) {
                recipeRepository.removeFavourite(drink.idDrink.toString())
            } else {
                recipeRepository.addFavourite(drink)
            }
            _viewState.update {
                it.copy(isFavourite = recipeRepository.isFavourite(drink.idDrink.toString()))
            }

        }
    }
}

private fun Drinks.mapToIngredientList(): List<UnitAndIngredients> {
    val ingredientList = listOf(
        UnitAndIngredients(this.strMeasure1.orEmpty(), this.strIngredient1.orEmpty()),
        UnitAndIngredients(this.strMeasure2.orEmpty(), this.strIngredient2.orEmpty()),
        UnitAndIngredients(this.strMeasure3.orEmpty(), this.strIngredient3.orEmpty()),
        UnitAndIngredients(this.strMeasure4.orEmpty(), this.strIngredient4.orEmpty()),
        UnitAndIngredients(this.strMeasure5.orEmpty(), this.strIngredient5.orEmpty()),
        UnitAndIngredients(this.strMeasure6.orEmpty(), this.strIngredient6.orEmpty()),
        UnitAndIngredients(this.strMeasure7.orEmpty(), this.strIngredient7.orEmpty()),
        UnitAndIngredients(this.strMeasure8.orEmpty(), this.strIngredient8.orEmpty()),
        UnitAndIngredients(this.strMeasure9.orEmpty(), this.strIngredient9.orEmpty()),
        UnitAndIngredients(this.strMeasure10.orEmpty(), this.strIngredient10.orEmpty()),
        UnitAndIngredients(this.strMeasure11.orEmpty(), this.strIngredient11.orEmpty()),
        UnitAndIngredients(this.strMeasure12.orEmpty(), this.strIngredient12.orEmpty()),
        UnitAndIngredients(this.strMeasure13.orEmpty(), this.strIngredient13.orEmpty()),
        UnitAndIngredients(this.strMeasure14.orEmpty(), this.strIngredient14.orEmpty()),
        UnitAndIngredients(this.strMeasure15.orEmpty(), this.strIngredient15.orEmpty()),
    ).filter { it.ingredient.isNotBlank() }

    Log.d("DrinksDetailsViewModel", "FilteredIngredients: $ingredientList")
    return ingredientList
}

