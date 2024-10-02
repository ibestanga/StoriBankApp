package com.ibra.dev.android.storibankapp.login.domain.models

import android.graphics.Bitmap
import com.ibra.dev.android.storibankapp.core.data.entities.UserEntity
import com.ibra.dev.android.storibankapp.core.utils.EMPTY_STRING

data class UserSingUpDto(
    val name: String = EMPTY_STRING,
    val surname: String = EMPTY_STRING,
    val email: String = EMPTY_STRING,
    val password: String = EMPTY_STRING,
    val dniPicture: Bitmap? = null
)

fun UserSingUpDto.isValid(): Boolean {
    return name.isNotEmpty() && surname.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && dniPicture != null
}

fun UserSingUpDto.toUserEntity(): UserEntity {
    return UserEntity(
        name = name,
        surname = surname,
        email = email,
        password = password,
    )
}
