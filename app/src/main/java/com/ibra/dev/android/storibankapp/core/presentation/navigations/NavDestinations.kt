package com.ibra.dev.android.storibankapp.core.presentation.navigations

import com.ibra.dev.android.storibankapp.register.presentations.screen.result.BobsitoState
import kotlinx.serialization.Serializable

@Serializable
object HomeDestination

@Serializable
object LoginDestination

@Serializable
object RegisterDestination

@Serializable
data class SingUpResultDestination(
    val msg: String,
    val state: BobsitoState
)