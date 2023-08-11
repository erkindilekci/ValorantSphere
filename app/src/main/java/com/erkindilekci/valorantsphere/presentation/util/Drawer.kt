package com.erkindilekci.valorantsphere.presentation.util

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DrawerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.erkindilekci.valorantsphere.R

@Composable
fun Drawer(
    drawerState: DrawerState,
    onAgentClick: () -> Unit,
    onWeaponClick: () -> Unit,
    onGameModClick: () -> Unit,
    onMapsClick: () -> Unit,
    content: @Composable () -> Unit
) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        content = content,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = MaterialTheme.colorScheme.primary,
                modifier = Modifier.width(LocalConfiguration.current.screenWidthDp.dp.div(1.75f))
            ) {
                NavigationDrawerItem(
                    label = {
                        Row(modifier = Modifier.padding(horizontal = 12.dp)) {
                            Text(
                                text = "Agents",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    color = Color.White, fontFamily = FontFamily(
                                        Font(
                                            R.font.poppins_medium, weight = FontWeight.Medium
                                        )
                                    )
                                )
                            )
                        }
                    },
                    colors = NavigationDrawerItemDefaults.colors(unselectedContainerColor = Color.Transparent),
                    selected = false,
                    shape = RectangleShape,
                    onClick = onAgentClick
                )

                NavigationDrawerItem(
                    label = {
                        Row(modifier = Modifier.padding(horizontal = 12.dp)) {
                            Text(
                                text = "Weapons",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    color = Color.White, fontFamily = FontFamily(
                                        Font(
                                            R.font.poppins_medium, weight = FontWeight.Medium
                                        )
                                    )
                                )
                            )
                        }
                    },
                    colors = NavigationDrawerItemDefaults.colors(unselectedContainerColor = Color.Transparent),
                    selected = false,
                    shape = RectangleShape,
                    onClick = onWeaponClick
                )

                NavigationDrawerItem(
                    label = {
                        Row(modifier = Modifier.padding(horizontal = 12.dp)) {
                            Text(
                                text = "Maps",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    color = Color.White, fontFamily = FontFamily(
                                        Font(
                                            R.font.poppins_medium, weight = FontWeight.Medium
                                        )
                                    )
                                )
                            )
                        }
                    },
                    colors = NavigationDrawerItemDefaults.colors(unselectedContainerColor = Color.Transparent),
                    selected = false,
                    shape = RectangleShape,
                    onClick = onMapsClick
                )

                NavigationDrawerItem(
                    label = {
                        Row(modifier = Modifier.padding(horizontal = 12.dp)) {
                            Text(
                                text = "Game Modes",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    color = Color.White, fontFamily = FontFamily(
                                        Font(
                                            R.font.poppins_medium, weight = FontWeight.Medium
                                        )
                                    )
                                )
                            )
                        }
                    },
                    colors = NavigationDrawerItemDefaults.colors(unselectedContainerColor = Color.Transparent),
                    selected = false,
                    shape = RectangleShape,
                    onClick = onGameModClick
                )
            }
        }
    )
}
