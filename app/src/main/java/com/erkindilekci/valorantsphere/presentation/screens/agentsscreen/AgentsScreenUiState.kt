package com.erkindilekci.valorantsphere.presentation.screens.agentsscreen

import com.erkindilekci.valorantsphere.data.remote.dto.agentlist.Agent

data class AgentsScreenUiState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val agents: List<Agent> = mutableListOf(),
    val message: String? = null
)
