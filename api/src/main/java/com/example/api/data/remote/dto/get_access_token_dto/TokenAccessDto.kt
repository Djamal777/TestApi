package com.example.api.data.remote.dto.get_access_token_dto


import com.example.api.domain.model.TokenAccess
import com.google.gson.annotations.SerializedName

data class TokenAccessDto(
    @SerializedName("accessToken")
    val accessToken: String,
    @SerializedName("result")
    val result: Result
)

fun TokenAccessDto.toTokenAccess():TokenAccess{
    return TokenAccess(accessToken)
}