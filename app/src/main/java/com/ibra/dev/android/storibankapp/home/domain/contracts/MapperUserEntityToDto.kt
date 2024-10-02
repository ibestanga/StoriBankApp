package com.ibra.dev.android.storibankapp.home.domain.contracts

import com.ibra.dev.android.storibankapp.core.data.entities.UserEntity
import com.ibra.dev.android.storibankapp.home.domain.models.HomeUserDto

interface MapperUserEntityToDto {

    fun invoke(userEntity: UserEntity?): HomeUserDto
}