package com.example.amphibians.fake

import com.example.amphibians.data.NetworkAmphibiansRepository
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test

class NetworkAmphRepositoryTest {
    @Test
    fun networkAmphibiansRepository_getAmphibians_verifyAmphibiansList() {
        runTest {
            val repository = NetworkAmphibiansRepository(
                    amphApiService = FakeAmphApiService()
            )
            assertEquals(FakeDataSource.amphibians, repository.getAmphibians())
        }
    }
}