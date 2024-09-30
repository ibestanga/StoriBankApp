package com.ibra.dev.android.storibankapp.core.data.entities

data class UserEntity(
    val name: String? = null,
    val surname: String? = null,
    val email: String? = null,
    val password: String? = null,
    val movements: List<MovementsEntity> = emptyList(),
    val balance: Double? = null,
    val idPicture: String? = null
)
