package com.erkindilekci.valorantsphere.presentation.screens.agentdetailsscreen

import com.erkindilekci.valorantsphere.data.remote.dto.agentdetails.AgentDetails

data class AgentDetailsScreenUiState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val agent: AgentDetails? = null,
    val message: String? = null
)
