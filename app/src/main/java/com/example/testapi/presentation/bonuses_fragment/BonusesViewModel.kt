package com.example.testapi.presentation.bonuses_fragment

import androidx.lifecycle.*
import com.example.api.data.remote.dto.get_access_token_dto.Params
import com.example.api.domain.model.Bonuses
import com.example.api.domain.model.TokenAccess
import com.example.api.domain.use_case.GetAccessTokenUseCase
import com.example.api.domain.use_case.GetBonusesUseCase
import com.example.api.other.Resource
import com.example.testapi.other.Constants.CLIENT_ID
import com.example.testapi.other.Constants.DEVICE_ID
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BonusesViewModel @Inject constructor(
    private val getAccessTokenUseCase: GetAccessTokenUseCase,
    private val getBonusesUseCase: GetBonusesUseCase,
) : ViewModel() {

    private val _user: MutableLiveData<Resource<TokenAccess>> = MutableLiveData()
    val user: LiveData<Resource<TokenAccess>> = _user

    private val _clientId: MutableLiveData<String> = MutableLiveData(CLIENT_ID)
    val clientId: LiveData<String> = _clientId

    private val _deviceId: MutableLiveData<String> = MutableLiveData(DEVICE_ID)
    val deviceId: LiveData<String> = _deviceId

    private val _bonusesInfo: MutableLiveData<Resource<Bonuses>> = MutableLiveData()
    val bonusesInfo: LiveData<Resource<Bonuses>> = _bonusesInfo

    private val _longitude: MutableLiveData<Double> = MutableLiveData(0.0)
    val longitude: LiveData<Double> = _longitude

    private val _latitude: MutableLiveData<Double> = MutableLiveData(0.0)
    val latitude: LiveData<Double> = _latitude

    init {
        viewModelScope.launch {
            getAccessToken(
                Params(
                    "",
                    clientId.value!!,
                    0,
                    0,
                    "device",
                    deviceId.value!!,
                    0
                )
            )
            delay(100)
            getBonuses()
        }
    }

    suspend fun getAccessToken(params: Params) {
        getAccessTokenUseCase(
            params
        ).collect { tokenAccess ->
            _user.postValue(tokenAccess)
        }
    }

    private suspend fun getBonuses() {
        user.value?.data?.accessToken?.let { accessToken ->
            getBonusesUseCase(accessToken).collect { bonuses ->
                _bonusesInfo.postValue(bonuses)
            }
        }
    }

    fun setLatitude(value: Double) {
        _latitude.value = value
    }

    fun setLongitude(value: Double) {
        _longitude.value = value
    }
}