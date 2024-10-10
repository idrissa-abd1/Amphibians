package com.example.amphibians.fake

import com.example.amphibians.network.AmphApiService
import com.example.amphibians.network.Amphibians

class FakeAmphApiService: AmphApiService {
    override suspend fun getAmphibians(): List<Amphibians> {
        return FakeDataSource.amphibians
    }
}