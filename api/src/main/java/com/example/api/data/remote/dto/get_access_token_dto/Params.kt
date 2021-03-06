package com.example.api.data.remote.dto.get_access_token_dto


import com.google.gson.annotations.SerializedName

data class Params(
    @SerializedName("accessToken")
    val accessToken: String,
    @SerializedName("idClient")
    val idClient: String,
    @SerializedName("latitude")
    val latitude: Int,
    @SerializedName("longitude")
    val longitude: Int,
    @SerializedName("paramName")
    val paramName: String,
    @SerializedName("paramValue")
    val paramValue: String,
    @SerializedName("sourceQuery")
    val sourceQuery: Int
)