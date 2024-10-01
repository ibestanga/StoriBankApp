package com.ibra.dev.android.storibankapp.core.utils

fun String?.orAlternative(alternative: String): String {
    return if (this.isNullOrEmpty()) alternative else this
}

const val EMPTY_STRING = ""