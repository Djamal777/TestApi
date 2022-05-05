package com.example.api.data.remote.dto.get_access_token_dto


import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("codeResult")
    val codeResult: Int,
    @SerializedName("duration")
    val duration: Int,
    @SerializedName("idLog")
    val idLog: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("messageDev")
    val messageDev: String,
    @SerializedName("status")
    val status: Int
)