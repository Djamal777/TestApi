package com.example.api.data.remote.dto.get_bonuses_info_dto


import com.example.api.domain.model.Bonuses
import com.google.gson.annotations.SerializedName

data class BonusesDto(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("resultOperation")
    val resultOperation: ResultOperation
)

fun BonusesDto.toBonuses(): Bonuses {
    val date="${data.dateBurning.substring(8,10)}.${data.dateBurning.substring(5,7)}"
    return Bonuses(
        data.currentQuantity,
        date,
        data.forBurningQuantity,
        data.typeBonusName
    )
}