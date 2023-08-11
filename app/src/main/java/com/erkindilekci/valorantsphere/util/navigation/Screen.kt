package com.erkindilekci.valorantsphere.util.navigation

sealed class Screen(val route: String) {
    object AgentsScreen : Screen("agents_screen")
    object AgentDetailsScreen : Screen("agent_details_screen/{id}") {
        fun passId(id: String): String {
            return "agent_details_screen/$id"
        }
    }
    object WeaponsScreen : Screen("weapons_screen")
    object WeaponDetailsScreen : Screen("weapon_details_screen/{id}") {
        fun passId(id: String): String {
            return "weapon_details_screen/$id"
        }
    }
    object MapsScreen : Screen("maps_screen")
    object GameModesScreen : Screen("gamemodes_screen")
}
