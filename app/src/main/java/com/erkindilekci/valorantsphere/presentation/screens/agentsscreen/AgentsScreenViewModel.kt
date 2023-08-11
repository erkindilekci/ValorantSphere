package com.erkindilekci.valorantsphere.presentation.screens.agentsscreen

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
class AgentsScreenViewModel @Inject constructor(
    private val repo: ValorantRepository
) : ViewModel() {

    private val _state = MutableStateFlow(AgentsScreenUiState())
    val state: StateFlow<AgentsScreenUiState> = _state.asStateFlow()

    init {
        getAllAgents()
    }

    private fun getAllAgents() {
        viewModelScope.launch {
            repo.getAllAgents().collectLatest {
                when (it) {
                    is Resource.Loading -> {
                        _state.update { state ->
                            state.copy(
                                isLoading = true,
                                isError = false,
                                agents = it.data?.data ?: emptyList()
                            )
                        }
                    }

                    is Resource.Success -> {
                        _state.update { state ->
                            state.copy(
                                isLoading = false,
                                agents = it.data?.data ?: emptyList()
                            )
                        }
                    }

                    is Resource.Error -> {
                        _state.update { state ->
                            state.copy(
                                isLoading = false,
                                isError = true,
                                agents = it.data?.data ?: emptyList(),
                                message = it.message
                            )
                        }
                    }
                }
            }
        }
    }
}
