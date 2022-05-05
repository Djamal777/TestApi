package com.example.api.domain.repository

import com.example.api.data.remote.dto.get_access_token_dto.Params
import com.example.api.data.remote.dto.get_access_token_dto.TokenAccessDto
import com.example.api.data.remote.dto.get_bonuses_info_dto.BonusesDto
import retrofit2.Response

interface BonusRepository {

    suspend fun getAccessToken(params: Params): TokenAccessDto

    suspend fun getBonusesInfo(accessToken: String): BonusesDto
}