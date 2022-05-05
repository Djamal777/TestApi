package com.example.testapi.presentation.bonuses_fragment

import android.Manifest
import android.content.ContentValues.TAG
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.api.data.remote.dto.get_access_token_dto.Params
import com.example.api.other.Resource
import com.example.testapi.R
import com.example.testapi.other.Constants.TIME_LIVE_ACCESS_TOKEN_CLIENT
import com.example.view.databinding.FragmentBonusesBinding
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import javax.inject.Inject

@AndroidEntryPoint
class BonusesFragment : Fragment() {

    @Inject
    lateinit var fusedLocationClient: FusedLocationProviderClient

    lateinit var binding: FragmentBonusesBinding

    val viewModel: BonusesViewModel by viewModels()

    var repeatableJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBonusesBinding.inflate(layoutInflater)
        binding.bonuses.textSize = 24F
        binding.burnBonuses.textSize = 14F
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeBonuses()

        repeatableJob = lifecycleScope.launchWhenStarted {
            while (isActive) {
                delay(2000)
                getLastKnownLocation()
                delay(TIME_LIVE_ACCESS_TOKEN_CLIENT)
            }
        }
        repeatableJob?.start()

    }

    private fun observeBonuses() {
        viewModel.bonusesInfo.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    Log.d(TAG, "observeBonuses: Loading...")
                }
                is Resource.Success -> {
                    binding.bonuses.text = getString(R.string.bonuses, it.data!!.currentQuantity)
                    binding.burnBonuses.text = getString(
                        R.string.burn_bonuses,
                        it.data!!.dateBurning,
                        it.data!!.forBurningQuantity
                    )
                    Log.d(TAG, "observeBonuses: ${it.data!!.typeBonusName} ")
                }
                is Resource.Error -> {
                    Toast.makeText(
                        requireContext(),
                        "Упс! Что-то пошло не так.",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.d(TAG, "observeBonuses: ${it.message.toString()} ")
                }
            }
        }
    }

    private fun getLastKnownLocation() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                101
            )
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    viewModel.setLongitude(location.longitude)
                    viewModel.setLatitude(location.latitude)
                    lifecycleScope.launchWhenStarted {
                        viewModel.getAccessToken(
                            Params(
                                viewModel.user.value?.data?.accessToken ?: "",
                                viewModel.clientId.value!!,
                                viewModel.latitude.value?.toInt() ?: 0,
                                viewModel.longitude.value?.toInt() ?: 0,
                                "device",
                                viewModel.deviceId.value!!,
                                0
                            )
                        )
                    }
                    Log.d(
                        TAG,
                        "getLastKnownLocation: longtitude: ${viewModel.longitude.value}, latitude:${viewModel.latitude.value}"
                    )
                }
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        repeatableJob?.cancel()
    }

}