package com.erkindilekci.valorantsphere.presentation.screens.gamemodsscreen

import com.erkindilekci.valorantsphere.data.remote.dto.gamemodes.GameMode

data class GameModesScreenUiState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val gameModes: List<GameMode> = mutableListOf(),
    val message: String? = null
)
