package com.example.homebarincompose.recipesearch.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.homebarincompose.recipesearch.SearchViewModel
import com.example.homebarincompose.ui.theme.HomeBarTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity: ComponentActivity() {
    private val viewModel: SearchViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
setContent{
    HomeBarTheme {
        val state by viewModel.viewState.collectAsState()
        Surface()
        {

        }

    }
}
    }
}