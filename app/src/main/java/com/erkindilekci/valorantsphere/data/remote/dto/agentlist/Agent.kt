package com.erkindilekci.valorantsphere.data.remote.dto.agentlist

data class Agent(
    val uuid: String,
    val displayName: String?,
    val description: String?,
    val displayIcon: String?,
    val fullPortrait: String?,
    val background: String?,
    val backgroundGradientColors: List<String>?
)
