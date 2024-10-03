package com.ibra.dev.android.storibankapp.home.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class MovementsDto(
    val type: TypeTransaction? = null,
    val amount: Double? = null,
    val date: String? = null,
    val description: String? = null
)
