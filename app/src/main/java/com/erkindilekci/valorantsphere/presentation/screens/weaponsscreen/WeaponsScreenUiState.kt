package com.erkindilekci.valorantsphere.presentation.screens.weaponsscreen

import com.erkindilekci.valorantsphere.data.remote.dto.weapon.Weapon

data class WeaponsScreenUiState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val weapons: List<Weapon> = mutableListOf(),
    val message: String? = null
)
