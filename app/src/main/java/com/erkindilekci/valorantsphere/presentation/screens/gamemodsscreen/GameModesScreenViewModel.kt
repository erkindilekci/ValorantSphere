package com.erkindilekci.valorantsphere.presentation.screens.gamemodsscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erkindilekci.valorantsphere.data.repository.ValorantRepository
import com.erkindilekci.valorantsphere.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameModesScreenViewModel @Inject constructor(
    private val repo: ValorantRepository
) : ViewModel() {

    private val _state = MutableStateFlow(GameModesScreenUiState())
    val state: StateFlow<GameModesScreenUiState> = _state.asStateFlow()

    init {
        getAllGameModes()
    }

    private fun getAllGameModes() {
        viewModelScope.launch {
            repo.getAllGameModes().collectLatest {
                when (it) {
                    is Resource.Loading -> {
                        _state.update { state ->
                            state.copy(
                                isLoading = true,
                                isError = false,
                                gameModes = it.data?.data ?: emptyList()
                            )
                        }
                    }

                    is Resource.Success -> {
                        _state.update { state ->
                            state.copy(
                                isLoading = false,
                                gameModes = it.data?.data ?: emptyList()
                            )
                        }
                    }

                    is Resource.Error -> {
                        _state.update { state ->
                            state.copy(
                                isLoading = false,
                                isError = true,
                                gameModes = it.data?.data ?: emptyList(),
                                message = it.message
                            )
                        }
                    }
                }
            }
        }
    }
}
