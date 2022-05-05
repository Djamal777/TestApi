package com.example.api.data.remote

import com.example.api.data.remote.dto.get_access_token_dto.Params
import com.example.api.data.remote.dto.get_access_token_dto.TokenAccessDto
import com.example.api.data.remote.dto.get_bonuses_info_dto.BonusesDto
import retrofit2.Response
import retrofit2.http.*

interface IProBonusApi {

    @POST("/api/v3/clients/accesstoken")
    suspend fun getAccessToken(
        @Body params: Params
    ): TokenAccessDto

    @GET("api/v3/ibonus/generalinfo/{AccessToken}")
    suspend fun getBonusesInfo(
        @Path("AccessToken") accessToken: String
    ): BonusesDto
}