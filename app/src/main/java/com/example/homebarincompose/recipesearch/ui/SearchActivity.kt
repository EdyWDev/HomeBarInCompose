package com.example.homebarincompose.recipesearch.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Surface
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.homebarincompose.recipesearch.SearchViewModel
import com.example.homebarincompose.ui.theme.HomeBarTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : ComponentActivity() {
    private val viewModel: SearchViewModel by viewModels()


    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            HomeBarTheme {
                val state by viewModel.viewState.collectAsState()
         //       val searchText by viewModel.searchText.collectAsState()
          //      val isSearching by viewModel.isSearching.collectAsState()
          //      val drinksRecipesList by viewModel.drinksRecipesList.collectAsState()
                Surface()
                {
                    SearchRecipe()
                }
           /*     Scaffold (
                    topBar = {
                        SearchBar(
                            query = searchText,//text showed on SearchBar
                            onQueryChange = viewModel::onSearchTextChange, //update the value of searchText
                            onSearch = viewModel::onSearchTextChange, //the callback to be invoked when the input service triggers the ImeAction.Search action
                            active = isSearching, // whether the user is searching or not
                            onActiveChange = {viewModel.onToogleSearch()}, // the callback to be invoked when this search bar's active state is changed
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                        ) {

                        }

                }){}*/

            }
        }
    }
}

@Composable
fun SearchRecipe() {

}

@Preview
@Composable
fun DefaultPreview() {
    HomeBarTheme {

        SearchRecipe()
    }
}