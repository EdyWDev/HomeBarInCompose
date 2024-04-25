package com.example.homebarincompose.welcome.ui

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.homebarincompose.HomeBarNavigationManager.HomeBarNavigationManager.navigateToSearchRecipe
import com.example.homebarincompose.R
import com.example.homebarincompose.ui.theme.HomeBarTheme
import com.example.homebarincompose.welcome.WelcomeViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class WelcomeActivity : ComponentActivity() {
    private val viewModel: WelcomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeBarTheme {
                val state by viewModel.viewState.collectAsState()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {

                    FirstView(
                        findARecipeClicked = { navigateToSearchRecipe() }
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FirstView(
    findARecipeClicked: () -> Unit
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "HomeBar",
                        color = Color.Black ,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues = innerPadding)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp, vertical = 4.dp)
                    .fillMaxWidth(),
               ) {
                Image(
                    painter = painterResource(id = R.drawable.pinkgin),
                    contentDescription = "?",
                    modifier = Modifier
                        .aspectRatio(16f / 16f)
                )
            }
            Button(
                onClick = findARecipeClicked,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                shape = RoundedCornerShape(corner = CornerSize(16.dp))
            ) {
                Text(
                    text = "FIND A RECIPE",
                    color = Color.Black
                )
            }
            Button(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                shape = RoundedCornerShape(corner = CornerSize(16.dp))
            ) {
                Text(
                    text = "FAVOURITE",
                    color = Color.Black
                )
            }
            Button(
                onClick = {},
                 modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                shape = RoundedCornerShape(corner = CornerSize(16.dp))
            ) {
                Text(
                    text = "RANDOM COCTAIL",
                    color = Color.Black
                )
            }


        }

    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DefaultPreview() {
    HomeBarTheme {
        FirstView(
            findARecipeClicked = {}
        )
    }
}