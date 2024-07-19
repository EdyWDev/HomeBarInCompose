package com.example.homebarincompose.recipesearch

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homebarincompose.coroutinesScopeUtils.safeLaunch
import com.example.homebarincompose.recipesearch.model.TypeOfSearchEnum
import com.example.homebarincompose.recipesearch.ui.SearchViewState
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
           // selectedValue = "",
            selectedTypeOfSearch = TypeOfSearchEnum.NAME,
            switchStateForName = false,
            switchStateForIngredients = false
        )
    )
    val viewState = _viewState.asStateFlow()

 fun searchForResult(userInput: String) {
        viewModelScope.safeLaunch(
            actionToTake = {
                Log.d("SearchViewModel", "searchForResult called with: $userInput")
                           val result = if(_viewState.value.selectedTypeOfSearch == TypeOfSearchEnum.NAME){
                               recipeRepository.getRecipeByCocktailName(userInput)
                           } else {
                               recipeRepository.getRecipeByIngredients(userInput)
                           }
                Log.d("SearchViewModel", "Search result: $result")
                _viewState.update {
                    it.copy(
                        drinksList = result.drinks,
                      //  selectedValue = result.toString()
                    )
                }
            }, onException = { error ->
                Log.e("MYAPP", "exception", error)

            }
        )
    }


        fun onSearchTextChange(text: String) {
            _viewState.update {
                it.copy(
                    searchText = text
                )
            }
            searchForResult(text)
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