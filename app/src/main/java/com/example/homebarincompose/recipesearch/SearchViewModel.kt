package com.example.homebarincompose.recipesearch

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homebarincompose.coroutinesScopeUtils.safeLaunch
import com.example.homebarincompose.recipesearch.model.RecipeRepository
import com.example.homebarincompose.recipesearch.model.RecipeSearchExtraData
import com.example.homebarincompose.recipesearch.model.TypeOfSearchEnum
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val recipeRepository: RecipeRepository
) : ViewModel() {


    //  private val extraData: RecipeSearchExtraData =
    //       requireNotNull(savedStateHandle[ExtraDataConst.EXTRA_DATA_RECIPE_SEARCH])

  //  private val _typeOfSearch = mutableListOf(TypeOfSearchEnum.NAME, TypeOfSearchEnum.INGREDIENTS)
 //   val typeOfSearch = _typeOfSearch.toList()

    private val _viewState = MutableStateFlow(
        SearchViewState(
            typeOfSearch = emptyList(),
            indexOfSelectedSearch = 0,
            searchViewMessage = "",
            response = null,
            spinnerGlassResponse = null
        )
    )
    val viewState: StateFlow<SearchViewState> = _viewState

    private fun updateViewState(update: SearchViewState.() -> SearchViewState) {
        _viewState.value = _viewState.value.update()
    }

    fun updateSelectedSearchIndex(index: Int) {
        updateViewState { copy(indexOfSelectedSearch = index) }
    }

    fun updateSearchViewMessage(message: String) {
        updateViewState { copy(searchViewMessage = message) }
    }
   // private val _typeOfSearch = mutableListOf(TypeOfSearchEnum.NAME, TypeOfSearchEnum.INGREDIENTS)

    //val indexOfTheSelectedValue =
    private fun isTypeOfSearchSelected(): TypeOfSearchEnum{
        return _viewState.value.typeOfSearch [_viewState.value.indexOfSelectedSearch ?: 0]
    }

     init{
        searchForResult()
   }

    private fun searchForResult(){
        viewModelScope.safeLaunch(
            actionToTake = {
                when(isTypeOfSearchSelected()){
                    TypeOfSearchEnum.NAME -> {
                        val response = recipeRepository.getRecipeByCocktailName(_viewState.value.searchViewMessage)
                     _viewState.update {
                                it.copy(drinksList = response.drinks )
                            }
                    }
                    TypeOfSearchEnum.INGREDIENTS -> {
                        val responseIngredients = recipeRepository.getRecipeByIngredients(_viewState.value.searchViewMessage)
                        _viewState.update {
                            it.copy(drinksList = responseIngredients.drinks)
                        }
                    }
                }
            },
            onException = {error->
                Log.e("MYAPP", "exception", error)
            }
        )
         /*{
            try{
                val resultResponse = recipeRepository.getRecipeByCocktailName(_viewState.value.toString())
                _viewState.update {
                    it.copy(response = resultResponse)
                }
            }
            catch (e: Exception){
                Log.e("EEE", "BŁĄD W VIEWMODEL, ${e.message}")
            }
        }*/
    }


    /*  fun searchForResult() {
        viewModelScope.safeLaunch(
            actionToTake = {
                when (
                    viewState.value.typeOfSearch[viewState.value.indexOfSelectedSearch]) {
                    TypeOfSearchEnum.INGREDIENTS -> {
                            val response = recipeRepository.getRecipeByIngredients(searchViewMessage.)
                        updateViewState {
                            copy(
                        )
                    }
                    TypeOfSearchEnum.NAME -> updateViewState {
                        copy(
                            response = recipeRepository.getRecipeByCocktailName(
                                searchViewMessage
                            )
                        )
                    }
                }


            },
            onException = { error ->
                Log.e("MYAPP", "exception", error)
            }
        )
    }*/


    //   val indexOfTheSelectedValue =

    /* // first state whether the search is happening or not
     private val _isSearching = MutableStateFlow(false)
     val isSearching = _isSearching.asStateFlow()

     // second state the text typed by the user
     private val _searchText = MutableStateFlow("")
     val searchText = _searchText.asStateFlow()


     // third state the list to be filtered
     // private val _drinkRecipeList = MutableStateFlow(drinks)
  //   val data = recipeRepository.getRecipeByIngredients(searchText.value.toString())
     val drinksRecipesList = searchText.combine(_viewState) { text, drinks ->
         if (text.isBlank()) {
           //  recipeRepository.getRecipeByIngredients(searchText.value)
             drinks
             // _viewState.value = drinks
         }
         //   drinks.drinksList.filter{
         //    }
     }.stateIn(
         scope = viewModelScope,
         started = SharingStarted.WhileSubscribed(5000), // --> it will alow the StateFlow survive 5 seconds before it been canceled
         initialValue = _viewState.value
     )

     // this method is used to updated what the user is typing on the SearchBar
     fun onSearchTextChange(text: String) {
         _searchText.value = text
     }

     //this method is to update if the user is searching or not and reset the searchText if the user is not searching
     fun onToogleSearch() {
         _isSearching.value = !_isSearching.value
         if (!_isSearching.value) {
             onSearchTextChange("")
         }
     }*/

}