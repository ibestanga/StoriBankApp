package com.ibra.dev.android.storibankapp.home.domain.models

import com.ibra.dev.android.storibankapp.core.utils.EMPTY_STRING
import kotlinx.serialization.Serializable

@Serializable
data class HomeUserDto(
    val name: String = EMPTY_STRING,
    val surname: String = EMPTY_STRING,
    val email: String = EMPTY_STRING,
    val movements: List<MovementsDto> = emptyList(),
    val balance: Double = 0.0,
    val urlDniPicture: String = EMPTY_STRING
)
