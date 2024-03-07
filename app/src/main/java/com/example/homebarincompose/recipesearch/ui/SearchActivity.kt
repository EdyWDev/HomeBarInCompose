package com.example.homebarincompose.recipesearch.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.homebarincompose.recipesearch.SearchViewModel
import com.example.homebarincompose.recipesearch.SearchViewState
import com.example.homebarincompose.recipesearch.model.Drinks
import com.example.homebarincompose.recipesearch.model.RecipeRepository
import com.example.homebarincompose.recipesearch.model.TypeOfSearchEnum
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

                Switch()
                SearchScreen(
                    onQueryChange = {viewModel::onSearchTextChange},
                    onSearch = { viewModel::onSearchTextChange },
                    onActiveChange = { viewModel.onToogleSearch()},
                    state = state
                )

            }
        }
    }
}
@Composable
fun Switch(){
    var isChecked by remember { mutableStateOf(false) }

    Switch(
        checked = isChecked,
        onCheckedChange = {isChecked = it},
        colors = SwitchDefaults.colors(
            checkedThumbColor = Color.Green,
            uncheckedThumbColor = Color.Gray,
            checkedTrackColor = Color.Green.copy(alpha=0.7f),
            uncheckedTrackColor = Color.Gray.copy(alpha = 0.7f)
        )
    )
}
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SearchScreen(
    onQueryChange: () -> Unit,
    onSearch: () -> Unit,
    onActiveChange: () -> Unit,
    state: SearchViewState
) {

    Scaffold(
        topBar = {
            SearchBar(
                query = state.searchText,
                onQueryChange = { onQueryChange.invoke() },
                onSearch = { onSearch.invoke() },
                active =  state.isSearching,
                onActiveChange = {onActiveChange.invoke() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                LazyColumn {
                    items(state.drinksList) { list ->
                            Text(
                                text = list.toString(),
                                modifier = Modifier.padding(
                                    start = 8.dp,
                                    top = 4.dp,
                                    end = 8.dp,
                                    bottom = 4.dp
                                )
                            )
                        }
                }
            }
        }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues = innerPadding)
                .padding(horizontal = 16.dp, vertical = 16.dp)
                .verticalScroll(rememberScrollState())
                .background(color = Color.Black),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            state.drinksList.forEach { item ->
                ListOfRecipeDrinks(items = item)
            }
        }

    }
}

@Composable
fun ListOfRecipeDrinks(items: Drinks) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)

    ) {
      /*  Text(
            text = items,
            modifier = Modifier.padding(16.dp),
            color = Color.Black,
            fontWeight = FontWeight.Black,
            fontStyle = FontStyle.Italic
        )*/
    }

}

@Preview
    @Composable
    fun DefaultPreview() {
        HomeBarTheme {

            Switch()
            SearchScreen(
                state = SearchViewState(
                    indexOfTheSelectedValue = (""),
                    isSearching = true,
                    searchText = (""),
                    selectedTypeOfSearch = TypeOfSearchEnum.NAME
                ),
                onQueryChange = {},
                onActiveChange = {},
                onSearch = {}
            )
        }
    }
