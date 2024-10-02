package com.ibra.dev.android.storibankapp.home.domain.usecase

import com.ibra.dev.android.storibankapp.core.data.entities.UserEntity
import com.ibra.dev.android.storibankapp.core.utils.orZero
import com.ibra.dev.android.storibankapp.home.domain.contracts.MapperUserEntityToDto
import com.ibra.dev.android.storibankapp.home.domain.models.HomeUserDto

class MapperUserEntityToDtoImpl : MapperUserEntityToDto {

    override fun invoke(userEntity: UserEntity?): HomeUserDto {
        return HomeUserDto(
            name = userEntity?.name.orEmpty(),
            surname = userEntity?.surname.orEmpty(),
            email = userEntity?.email.orEmpty(),
            movements = userEntity?.movements.orEmpty(),
            balance = userEntity?.balance.orZero(),
            urlDniPicture = userEntity?.urlDniPicture.orEmpty()
        )
    }
}