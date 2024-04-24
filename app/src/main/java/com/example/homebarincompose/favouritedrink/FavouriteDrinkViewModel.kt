package com.example.homebarincompose.favouritedrink

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.homebarincompose.service.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteDrinkViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository
): ViewModel(){

    private val _viewState = MutableStateFlow(value = FavouriteDrinkViewState())
    val viewState = _viewState.asStateFlow()



}