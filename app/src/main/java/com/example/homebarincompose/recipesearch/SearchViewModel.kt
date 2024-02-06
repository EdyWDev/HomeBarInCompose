package com.example.homebarincompose.recipesearch

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(): ViewModel() {

    private val _viewState = MutableStateFlow(value = SearchViewState())
    val viewState = _viewState.asStateFlow()
}