package com.example.homebarincompose.welcome

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
) : ViewModel() {


    private val _viewState = MutableStateFlow(value = WelcomeViewState())
    val viewState = _viewState.asStateFlow()

}