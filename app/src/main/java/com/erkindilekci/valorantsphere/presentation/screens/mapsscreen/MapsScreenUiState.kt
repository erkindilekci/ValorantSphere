package com.erkindilekci.valorantsphere.presentation.screens.mapsscreen

import com.erkindilekci.valorantsphere.data.remote.dto.maps.Map

data class MapsScreenUiState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val maps: List<Map> = mutableListOf(),
    val message: String? = null
)
