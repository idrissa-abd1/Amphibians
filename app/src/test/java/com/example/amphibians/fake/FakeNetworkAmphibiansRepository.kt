package com.example.amphibians.fake

import com.example.amphibians.data.AmphibiansRepository
import com.example.amphibians.network.Amphibians
import com.example.amphibians.rules.TestDispatcherRule
import com.example.amphibians.ui.Screens.AmphiUiState
import com.example.amphibians.ui.Screens.AmphiViewModel
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class FakeNetworkAmphibiansRepository : AmphibiansRepository {
    override suspend fun getAmphibians(): List<Amphibians> {
        return FakeDataSource.amphibians
    }
}

class AmphiViewModelTest{
    @get:Rule
    val testDispatcher = TestDispatcherRule()
    @Test
    fun amphiViewModel_getAmphibians_verifyAmphibianUiStateSuccess() =
            runTest {
                val amphibiansViewModel = AmphiViewModel(
                        amphibiansRepository = FakeNetworkAmphibiansRepository()
                )
                assertEquals(
                        AmphiUiState.Success(FakeDataSource.amphibians),
                        amphibiansViewModel.uiState
                )
            }
}