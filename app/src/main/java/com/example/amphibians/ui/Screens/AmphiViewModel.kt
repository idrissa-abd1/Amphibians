package com.example.amphibians.ui.Screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.amphibians.AmphibiansApplication
import com.example.amphibians.data.AmphibiansRepository
import com.example.amphibians.network.Amphibians
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface AmphiUiState {
    data class Success(val amphibians: List<Amphibians>) : AmphiUiState
    object Error : AmphiUiState
    object Loading : AmphiUiState
}

class AmphiViewModel(
    private val amphibiansRepository: AmphibiansRepository
) : ViewModel() {
    var uiState: AmphiUiState by mutableStateOf(AmphiUiState.Loading)
        private set

    init {
        getAmphibians()
    }

    fun getAmphibians() {
        viewModelScope.launch {
            uiState = try {
                AmphiUiState.Success(amphibiansRepository.getAmphibians())
            } catch (e: IOException) {
                AmphiUiState.Error
            } catch (e: HttpException) {
                AmphiUiState.Error
            }
        }
    }

    companion object{
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as AmphibiansApplication)
                val amphibiansRepository = application.container.amphibiansRepository
                AmphiViewModel(amphibiansRepository = amphibiansRepository)
            }
        }
    }
}