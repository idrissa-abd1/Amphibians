package com.example.amphibians.data

import com.example.amphibians.network.AmphApiService
import com.example.amphibians.network.Amphibians

interface AmphibiansRepository {
    suspend fun getAmphibians(): List<Amphibians>
}

class NetworkAmphibiansRepository(
        private val amphApiService: AmphApiService
): AmphibiansRepository {
    override suspend fun getAmphibians(): List<Amphibians> = amphApiService.getAmphibians()
}