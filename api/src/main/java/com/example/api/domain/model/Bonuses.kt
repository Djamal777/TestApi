package com.example.api.domain.model


data class Bonuses(
    val currentQuantity: Int,
    val dateBurning: String,
    val forBurningQuantity: Int,
    val typeBonusName: String
)