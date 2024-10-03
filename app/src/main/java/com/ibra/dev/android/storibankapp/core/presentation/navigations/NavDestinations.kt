package com.ibra.dev.android.storibankapp.core.presentation.navigations

import com.ibra.dev.android.storibankapp.home.domain.models.TypeTransaction
import com.ibra.dev.android.storibankapp.register.presentations.screen.result.BobsitoState
import kotlinx.serialization.Serializable

@Serializable
data class HomeDestination(val email: String)

@Serializable
object LoginDestination

@Serializable
object RegisterDestination

@Serializable
data class SingUpResultDestination(
    val msg: String,
    val state: BobsitoState
)

@Serializable
data class MovementsDetailsDestination(
    val typeTransaction: TypeTransaction,
    val amount: String,
    val date: String,
    val description: String
)