package com.erkindilekci.valorantsphere.util.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.erkindilekci.valorantsphere.presentation.screens.agentdetailsscreen.AgentDetailsScreen
import com.erkindilekci.valorantsphere.presentation.screens.agentsscreen.AgentsScreen
import com.erkindilekci.valorantsphere.presentation.screens.gamemodsscreen.GameModesScreen
import com.erkindilekci.valorantsphere.presentation.screens.mapsscreen.MapsScreen
import com.erkindilekci.valorantsphere.presentation.screens.weapondetailsscreen.WeaponDetailsScreen
import com.erkindilekci.valorantsphere.presentation.screens.weaponsscreen.WeaponsScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.AgentsScreen.route) {
        composable(Screen.AgentsScreen.route) {
            AgentsScreen(navController)
        }
        composable(Screen.AgentDetailsScreen.route) {
            AgentDetailsScreen(navController = navController)
        }
        composable(Screen.WeaponsScreen.route) {
            WeaponsScreen(navController = navController)
        }
        composable(Screen.WeaponDetailsScreen.route) {
            WeaponDetailsScreen(navController = navController)
        }
        composable(Screen.MapsScreen.route) {
            MapsScreen(navController = navController)
        }
        composable(Screen.GameModesScreen.route) {
            GameModesScreen(navController = navController)
        }
    }
}