package com.example.api.data.remote.dto.get_bonuses_info_dto


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("currentQuantity")
    val currentQuantity: Int,
    @SerializedName("dateBurning")
    val dateBurning: String,
    @SerializedName("forBurningQuantity")
    val forBurningQuantity: Int,
    @SerializedName("typeBonusName")
    val typeBonusName: String
)