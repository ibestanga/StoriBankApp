package com.ibra.dev.android.storibankapp.home.domain.usecase

import com.ibra.dev.android.storibankapp.core.data.entities.MovementsEntity
import com.ibra.dev.android.storibankapp.core.data.entities.UserEntity
import com.ibra.dev.android.storibankapp.core.utils.orZero
import com.ibra.dev.android.storibankapp.home.domain.contracts.MapperUserEntityToDto
import com.ibra.dev.android.storibankapp.home.domain.models.HomeUserDto
import com.ibra.dev.android.storibankapp.home.domain.models.MovementsDto
import com.ibra.dev.android.storibankapp.home.domain.models.TypeTransaction

class MapperUserEntityToDtoImpl : MapperUserEntityToDto {

    override fun invoke(userEntity: UserEntity?): HomeUserDto {
        return HomeUserDto(
            name = userEntity?.name.orEmpty(),
            surname = userEntity?.surname.orEmpty(),
            email = userEntity?.email.orEmpty(),
            movements = mapMovementsEntityToDto(userEntity?.movements.orEmpty()),
            balance = userEntity?.balance.orZero(),
            urlDniPicture = userEntity?.urlDniPicture.orEmpty()
        )
    }

    private fun mapMovementsEntityToDto(movementsEntity: List<MovementsEntity>): List<MovementsDto> {
        return movementsEntity.map {
            MovementsDto(
                type = mapTractionType(it.type.orEmpty()),
                amount = it.amount.orZero(),
                date = it.date.orEmpty(),
                description = it.description.orEmpty()
            )
        }
    }

    private fun mapTractionType(type: String): TypeTransaction {
        return when (type) {
            "DEPOSIT" -> TypeTransaction.DEPOSIT
            "WITHDRAWAL" -> TypeTransaction.WITHDRAWAL
            else -> TypeTransaction.DEPOSIT
        }
    }
}