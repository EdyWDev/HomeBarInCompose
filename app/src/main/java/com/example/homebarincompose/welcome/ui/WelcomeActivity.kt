package com.example.homebarincompose.welcome.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.homebarincompose.ui.theme.HomeBarInComposeTheme
import com.example.homebarincompose.welcome.WelcomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WelcomeActivity: ComponentActivity() {
    private val viewModel: WelcomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContent{
            HomeBarInComposeTheme {
                val state by viewModel.viewState.collectAsState()
                Surface () {

                }
            }
        }
    }
}