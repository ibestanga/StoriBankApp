package com.ibra.dev.android.storibankapp.core.utils

fun Double?.orZero(): Double {
    return this ?: 0.0
}