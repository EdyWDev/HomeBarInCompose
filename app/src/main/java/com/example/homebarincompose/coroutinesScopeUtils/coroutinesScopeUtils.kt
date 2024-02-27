package com.example.homebarincompose.coroutinesScopeUtils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


fun CoroutineScope.safeLaunch(
    actionToTake: suspend () -> Unit,
    onException: (Exception) -> Unit
) = this.launch {
    try {
        actionToTake()
    } catch (error: Exception) {
        onException(error)
    }
}