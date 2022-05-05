package com.example.api.domain.use_case

import com.example.api.data.remote.dto.get_access_token_dto.Params
import com.example.api.data.remote.dto.get_access_token_dto.toTokenAccess
import com.example.api.domain.model.TokenAccess
import com.example.api.domain.repository.BonusRepository
import com.example.api.other.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class GetAccessTokenUseCase @Inject constructor(
    private val repository: BonusRepository
) {
    operator fun invoke(params: Params): Flow<Resource<TokenAccess>> = flow {
        try {
            emit(Resource.Loading<TokenAccess>())
            val accessToken = repository.getAccessToken(params).toTokenAccess()
            emit(Resource.Success<TokenAccess>(accessToken))
        } catch (e: HttpException) {
            emit(Resource.Error<TokenAccess>(e.localizedMessage ?: "An unexpected message occured"))
        } catch (e: IOException) {
            emit(Resource.Error<TokenAccess>("Couldn't reach server. Check your internet connection"))
        }
    }
}