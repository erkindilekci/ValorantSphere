package com.erkindilekci.valorantsphere.data.remote.dto.gamemodes

data class GameMode(
    val allowsMatchTimeouts: Boolean,
    val assetPath: String,
    val displayIcon: String,
    val displayName: String,
    val duration: String,
    val gameFeatureOverrides: List<GameFeatureOverride>,
    val gameRuleBoolOverrides: List<GameRuleBoolOverride>,
    val isMinimapHidden: Boolean,
    val isTeamVoiceAllowed: Boolean,
    val orbCount: Int,
    val roundsPerHalf: Int,
    val teamRoles: List<String>,
    val uuid: String
)