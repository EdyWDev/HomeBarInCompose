package com.example.homebarincompose.recipesearch

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.homebarincompose.coroutinesScopeUtils.safeLaunch
import com.example.homebarincompose.recipesearch.model.RecipeRepository
import com.example.homebarincompose.recipesearch.model.TypeOfSearchEnum
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
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
        indexOfTheSelectedValue = "",
        selectedTypeOfSearch = TypeOfSearchEnum.NAME
        )
    )
    val viewState = _viewState

    init{
        searchForResult()
    }

    /* actionToTake = {
                val response = recipeRepository.getRecipeByCocktailName(searchText.value)
                _viewState.update {
                    it.copy(response.drinks?.toList().orEmpty())
                }
            },
            onException = { error ->
                Log.e("MYAPP", "exception", error)
            }*/
    fun searchForResult(){
        viewModelScope.safeLaunch(
            actionToTake ={
                if(_viewState.value.selectedTypeOfSearch == TypeOfSearchEnum.NAME){
                    val response = recipeRepository.getRecipeByCocktailName(_viewState.value.searchText)
                    }
                else{
                    recipeRepository.getRecipeByIngredients(_viewState.value.toString())
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

     /*   fun updateSearchText() {
            _viewState.update {
                it.copy(
                    searchText = it.searchText
                )
            }

        }*/
    }
}