package com.erkindilekci.valorantsphere.presentation.screens.weapondetailsscreen

import com.erkindilekci.valorantsphere.data.remote.dto.weapon.Weapon

data class WeaponDetailsScreenUiState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val weapon: Weapon? = null,
    val message: String? = null
)
