package com.ibra.dev.android.storibankapp.login.domain.models

import com.ibra.dev.android.storibankapp.core.data.entities.UserEntity
import com.ibra.dev.android.storibankapp.core.utils.EMPTY_STRING

data class UserSingUpDto(
    val name: String = EMPTY_STRING,
    val surname: String = EMPTY_STRING,
    val email: String = EMPTY_STRING,
    val password: String = EMPTY_STRING,
    val dniPicture: String = EMPTY_STRING
)

fun UserSingUpDto.isValid(): Boolean {
    return name.isNotEmpty() && surname.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()
}

fun UserSingUpDto.toUserEntity(): UserEntity {
    return UserEntity(
        name = name,
        surname = surname,
        email = email,
        password = password,
        dniPicture = dniPicture
    )
}
