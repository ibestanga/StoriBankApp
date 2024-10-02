package com.ibra.dev.android.storibankapp.home.domain.models

import com.ibra.dev.android.storibankapp.core.data.entities.MovementsEntity
import com.ibra.dev.android.storibankapp.core.utils.EMPTY_STRING

data class HomeUserDto(
    val name: String = EMPTY_STRING,
    val surname: String = EMPTY_STRING,
    val email: String = EMPTY_STRING,
    val movements: List<MovementsEntity> = emptyList(),
    val balance: Double = 0.0,
    val urlDniPicture: String = EMPTY_STRING
)
