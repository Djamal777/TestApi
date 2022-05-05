package com.example.api.domain.use_case

import com.example.api.data.remote.dto.get_bonuses_info_dto.toBonuses
import com.example.api.domain.model.Bonuses
import com.example.api.domain.repository.BonusRepository
import com.example.api.other.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetBonusesUseCase @Inject constructor(
    private val repository: BonusRepository
) {
    operator fun invoke(accessToken: String): Flow<Resource<Bonuses>> = flow {
        try {
            emit(Resource.Loading<Bonuses>())
            val bonuses = repository.getBonusesInfo(accessToken).toBonuses()
            emit(Resource.Success<Bonuses>(bonuses))
        } catch (e: HttpException) {
            emit(Resource.Error<Bonuses>(e.localizedMessage ?: "An unexpected message occured"))
        } catch (e: IOException) {
            emit(Resource.Error<Bonuses>("Couldn't reach server. Check your internet connection"))
        }
    }
}