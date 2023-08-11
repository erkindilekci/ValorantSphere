package com.erkindilekci.valorantsphere.presentation.screens.weapondetailsscreen

import androidx.lifecycle.SavedStateHandle
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
class WeaponDetailsScreenViewModel @Inject constructor(
    private val repo: ValorantRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(WeaponDetailsScreenUiState())
    val state: StateFlow<WeaponDetailsScreenUiState> = _state.asStateFlow()

    init {
        val id = savedStateHandle.get<String>("id")
        id?.let {
            getWeaponDetails(it)
        }
    }

    private fun getWeaponDetails(id: String) {
        viewModelScope.launch {
            repo.getWeaponDetails(id).collectLatest {
                when (it) {
                    is Resource.Loading -> {
                        _state.update { state ->
                            state.copy(
                                isLoading = true,
                                isError = false,
                                weapon = it.data?.data
                            )
                        }
                    }

                    is Resource.Success -> {
                        _state.update { state ->
                            state.copy(
                                isLoading = false,
                                weapon = it.data?.data
                            )
                        }
                    }

                    is Resource.Error -> {
                        _state.update { state ->
                            state.copy(
                                isLoading = false,
                                isError = true,
                                weapon = it.data?.data,
                                message = it.message
                            )
                        }
                    }
                }
            }
        }
    }
}
