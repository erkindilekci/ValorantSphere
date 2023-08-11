package com.erkindilekci.valorantsphere.presentation.screens.weaponsscreen

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
class WeaponsScreenViewModel @Inject constructor(
    private val repo: ValorantRepository
) : ViewModel() {

    private val _state = MutableStateFlow(WeaponsScreenUiState())
    val state: StateFlow<WeaponsScreenUiState> = _state.asStateFlow()

    init {
        getAllWeapons()
    }

    private fun getAllWeapons() {
        viewModelScope.launch {
            repo.getAllWeapons().collectLatest {
                when (it) {
                    is Resource.Loading -> {
                        _state.update { state ->
                            state.copy(
                                isLoading = true,
                                isError = false,
                                weapons = it.data?.data ?: emptyList()
                            )
                        }
                    }

                    is Resource.Success -> {
                        _state.update { state ->
                            state.copy(
                                isLoading = false,
                                weapons = it.data?.data ?: emptyList()
                            )
                        }
                    }

                    is Resource.Error -> {
                        _state.update { state ->
                            state.copy(
                                isLoading = false,
                                isError = true,
                                weapons = it.data?.data ?: emptyList(),
                                message = it.message
                            )
                        }
                    }
                }
            }
        }
    }
}
