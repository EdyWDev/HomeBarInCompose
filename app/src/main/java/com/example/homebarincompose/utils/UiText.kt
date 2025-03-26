package com.example.homebarincompose.utils

import android.content.Context

sealed class UiText {
    data class DynamicString(val value: String) : UiText()
    data class StringResource(
        val id: Int,
        val args: List<Any> = emptyList()
    ) : UiText()

    fun asString(context: Context): String {
        return when (this) {
            is DynamicString -> value
            is StringResource -> context.getString(
                id,
                *args.toTypedArray()
            )
        }
    }
}
