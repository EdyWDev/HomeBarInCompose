package com.example.homebarincompose.recipesearch

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homebarincompose.coroutinesScopeUtils.safeLaunch
import com.example.homebarincompose.recipesearch.model.TypeOfSearchEnum
import com.example.homebarincompose.service.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(
   // private val savedStateHandle: SavedStateHandle,
    private val recipeRepository: RecipeRepository,
) : ViewModel() {

    private val _viewState = MutableStateFlow(
        SearchViewState(
            drinksList = emptyList(),
            isSearching = false,
            searchText = "",
            selectedValue = "",
            selectedTypeOfSearch = TypeOfSearchEnum.NAME,
            switchStateForName = false,
            switchStateForIngredients = false
        )
    )
    val viewState = _viewState


    init {
        searchForResult()

    }


 fun searchForResult() {
        viewModelScope.safeLaunch(
            actionToTake = {

                // CZY TO ZAPYTANIE JEST OKK???
                if (_viewState.value.switchStateForName) {
                    _viewState.value.selectedValue =
                        recipeRepository.getRecipeByCocktailName(_viewState.value.selectedValue).toString()
                      //  recipeRepository.getRecipeByCocktailName()
                    /*recipeRepository.getRecipeByCocktailName(_viewState.value.searchText)*/
                } else {
                    _viewState.value.selectedValue =
                        recipeRepository.getRecipeByIngredients(_viewState.value.selectedValue).toString()
                }
            }, onException = { error ->
                Log.e("MYAPP", "exception", error)

            }
        )
    }


        fun onSearchTextChange(text: String) {
            _viewState.update {
                it.copy(
                    searchText = it.searchText
                )
            }
        }

        fun onToogleSearch() {
            _viewState.update {
                it.copy(
                    isSearching = it.isSearching.not()
                )
            }
        }

        fun onToogleCategoryOfSearch(type: TypeOfSearchEnum) {
            _viewState.update {
                it.copy(
                    selectedTypeOfSearch = type
                )
            }
        }

}