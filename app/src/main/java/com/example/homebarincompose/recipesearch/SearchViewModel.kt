package com.example.homebarincompose.recipesearch

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homebarincompose.coroutinesScopeUtils.safeLaunch
import com.example.homebarincompose.recipesearch.model.RecipeRepository
import com.example.homebarincompose.recipesearch.model.TypeOfSearchEnum
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(
   // private val savedStateHandle: SavedStateHandle,
    private val recipeRepository: RecipeRepository
) : ViewModel() {

    private val _viewState = MutableStateFlow(
        SearchViewState(
          //  typeOfSearch = emptyList(),
        //    indexOfSelectedSearch = 0,
         //   searchViewMessage = "",
         //   response = null,
         //   spinnerGlassResponse = null
        )
    )

    // val viewState: StateFlow<SearchViewState> = _viewState
    val viewState = _viewState.asStateFlow()


    //first state whether the search is happening or not
    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    // second state the text typed by the user
    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    val listOfDrink = viewState.value.drinksList

    //third state the list to be filtered
    //chyba z _listofdrinkrecipe jest problem ???
    private val _listOfDrinkRecipe = MutableStateFlow(listOfDrink)
    val listOfDrinkRecipe = searchText
        .combine(_listOfDrinkRecipe) { text, listOfDrink -> // combine searchText with _listOfDrinksRecipe
            if (text.isBlank()) { // return the entery list of countries if not is typed
                result()
                listOfDrink
            }
            listOfDrink.filter { drinksList ->

                drinksList.strDrink?.uppercase()?.contains((text.trim().uppercase()))
                    ?: throw Error("COS TU NIE GRA")
                //CZY TAK MOZE BYC? CZY JAK ZMIENIC TEN SAFE CALL
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = _listOfDrinkRecipe.value
        )

    fun result(){
        viewModelScope.safeLaunch(
            actionToTake = {
               val response = recipeRepository.getRecipeByCocktailName(searchText.value)
                _viewState.update{
                    it.copy(response.drinks?.toList().orEmpty())
                }
                  },
            onException = {
                    error->
                Log.e("MYAPP", "exception", error)
            }
          /*  actionToTake ={
                when{
                   isSearching -> _searchText.value =
                        recipeRepository.getRecipeByCocktailName(searchText.value).toString()
                }
            } ,
            onException = {
                    error->
                Log.e("MYAPP", "exception", error)
            }*/
        )

    }
    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    fun onToogleSearch() {
        _isSearching.value = !_isSearching.value
        if (!_isSearching.value) {
            onSearchTextChange("")
        }
    }

  //  private fun isTypeOfSearchSelected(): TypeOfSearchEnum {
  //      return _viewState.value.typeOfSearch[_viewState.value.indexOfSelectedSearch ?: 0]
   // }

  /*  init {
        searchForResult()
    }

    private fun searchForResult() {
        viewModelScope.safeLaunch(
            actionToTake = {
                when (isTypeOfSearchSelected()) {
                    TypeOfSearchEnum.NAME -> {
                        val response =
                            recipeRepository.getRecipeByCocktailName(_viewState.value.searchViewMessage)
                        _viewState.update {
                            it.copy(drinksList = response.drinks)
                        }
                    }
                    TypeOfSearchEnum.INGREDIENTS -> {
                        val responseIngredients =
                            recipeRepository.getRecipeByIngredients(_viewState.value.searchViewMessage)
                        _viewState.update {
                            it.copy(drinksList = responseIngredients.drinks)
                        }
                    }
                }
            },
            onException = { error ->
                Log.e("MYAPP", "exception", error)
            }
        )
    }*/

}