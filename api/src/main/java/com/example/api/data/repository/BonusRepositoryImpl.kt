package com.example.api.data.repository

import com.example.api.data.remote.IProBonusApi
import com.example.api.data.remote.dto.get_access_token_dto.Params
import com.example.api.data.remote.dto.get_access_token_dto.TokenAccessDto
import com.example.api.data.remote.dto.get_bonuses_info_dto.BonusesDto
import com.example.api.domain.repository.BonusRepository
import retrofit2.Response
import javax.inject.Inject

class BonusRepositoryImpl @Inject constructor(
    private val api: IProBonusApi
) : BonusRepository {
    override suspend fun getAccessToken(params: Params): TokenAccessDto {
        return api.getAccessToken(params)
    }

    override suspend fun getBonusesInfo(accessToken: String): BonusesDto {
        return api.getBonusesInfo(accessToken)
    }
}