package com.erkindilekci.valorantsphere.presentation.screens.weapondetailsscreen

import android.graphics.Color.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.erkindilekci.valorantsphere.R
import java.util.Locale


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WeaponDetailsScreen(
    navController: NavController, viewModel: WeaponDetailsScreenViewModel = hiltViewModel()
) {
    var isPlaying by remember { mutableStateOf(true) }
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.bg))

    val animationState by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever,
        isPlaying = isPlaying,
        restartOnPlay = true
    )

    val pickerState = rememberPagerState(Int.MAX_VALUE / 2)

    val agentViewerState = rememberPagerState(Int.MAX_VALUE / 2)

    LaunchedEffect(key1 = pickerState.currentPage) {
        if (pickerState.currentPage != agentViewerState.currentPage) {
            isPlaying = false
            agentViewerState.animateScrollToPage(pickerState.currentPage)
            isPlaying = true
        }
    }

    LaunchedEffect(key1 = agentViewerState.currentPage) {
        if (agentViewerState.currentPage != pickerState.currentPage) {
            isPlaying = false
            pickerState.animateScrollToPage(agentViewerState.currentPage)
            isPlaying = true
        }
    }

    val state by viewModel.state.collectAsState()

    val colors = listOf(
        MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.background
    )

    Scaffold { paddingValues ->
        Box(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(brush = Brush.verticalGradient(colors = colors, endY = -0.5f))
            ) {}

            LottieAnimation(
                modifier = Modifier.alpha(0.4f),
                composition = composition,
                progress = { animationState },
                contentScale = ContentScale.Fit
            )
        }
        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        if (!state.isLoading) {
            state.weapon?.let { weapon ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        IconButton(
                            onClick = { navController.popBackStack() },
                            modifier = Modifier
                                .padding(16.dp)
                                .align(CenterStart)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = null,
                                modifier = Modifier.size(30.dp),
                                tint = Color.White
                            )
                        }

                        Text(
                            text = weapon.displayName.uppercase(Locale.ROOT),
                            modifier = Modifier.align(Center),
                            style = MaterialTheme.typography.displaySmall.copy(
                                color = Color.White, fontFamily = FontFamily(
                                    Font(
                                        R.font.valorant_font, weight = FontWeight.Bold
                                    )
                                ), textAlign = TextAlign.Center
                            ),
                            color = Color.White,
                            fontSize = 30.sp
                        )
                    }

                    AsyncImage(
                        model = weapon.displayIcon,
                        contentDescription = null,
                        modifier = Modifier
                            .size(200.dp)
                            .align(CenterHorizontally)
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    Text(
                        text = "Weapon Stats",
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(horizontal = 8.dp),
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = Color.White, fontFamily = FontFamily(
                                Font(
                                    R.font.poppins_sb, weight = FontWeight.SemiBold
                                )
                            ), textAlign = TextAlign.Center
                        )
                    )

                    Text(
                        text = "Fire Rate: ${weapon.weaponStats.fireRate}",
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(horizontal = 8.dp),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = Color.White, fontFamily = FontFamily(
                                Font(
                                    R.font.poppins_medium, weight = FontWeight.Medium
                                )
                            ), textAlign = TextAlign.Center
                        )
                    )
                    Text(
                        text = "Magazine Size: ${weapon.weaponStats.magazineSize}",
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(horizontal = 8.dp),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = Color.White, fontFamily = FontFamily(
                                Font(
                                    R.font.poppins_medium, weight = FontWeight.Medium
                                )
                            ), textAlign = TextAlign.Center
                        )
                    )
                    Text(
                        text = "Run Speed Multiplier: ${weapon.weaponStats.runSpeedMultiplier}",
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(horizontal = 8.dp),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = Color.White, fontFamily = FontFamily(
                                Font(
                                    R.font.poppins_medium, weight = FontWeight.Medium
                                )
                            ), textAlign = TextAlign.Center
                        )
                    )
                    Text(
                        text = "Equip Time Seconds: ${weapon.weaponStats.equipTimeSeconds}",
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(horizontal = 8.dp),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = Color.White, fontFamily = FontFamily(
                                Font(
                                    R.font.poppins_medium, weight = FontWeight.Medium
                                )
                            ), textAlign = TextAlign.Center
                        )
                    )
                    Text(
                        text = "Reload Time Seconds: ${weapon.weaponStats.reloadTimeSeconds}",
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(horizontal = 8.dp),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = Color.White, fontFamily = FontFamily(
                                Font(
                                    R.font.poppins_medium, weight = FontWeight.Medium
                                )
                            ), textAlign = TextAlign.Center
                        )
                    )
                    Text(
                        text = "First Bullet Accuracy: ${weapon.weaponStats.firstBulletAccuracy}",
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(horizontal = 8.dp),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = Color.White, fontFamily = FontFamily(
                                Font(
                                    R.font.poppins_medium, weight = FontWeight.Medium
                                )
                            ), textAlign = TextAlign.Center
                        )
                    )
                    Text(
                        text = "Shotgun Pellet Count: ${weapon.weaponStats.shotgunPelletCount}",
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(horizontal = 8.dp),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = Color.White, fontFamily = FontFamily(
                                Font(
                                    R.font.poppins_medium, weight = FontWeight.Medium
                                )
                            ), textAlign = TextAlign.Center
                        )
                    )
                    weapon.weaponStats.damageRanges.forEach {
                        Text(
                            text = "Damage (${it.rangeStartMeters}-${it.rangeEndMeters})m:",
                            modifier = Modifier
                                .align(Alignment.Start)
                                .padding(horizontal = 8.dp),
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = Color.White, fontFamily = FontFamily(
                                    Font(
                                        R.font.poppins_medium, weight = FontWeight.Medium
                                    )
                                ), textAlign = TextAlign.Center
                            )
                        )
                        Text(
                            text = "Head Damage: ${it.headDamage}",
                            modifier = Modifier
                                .align(Alignment.Start)
                                .padding(horizontal = 24.dp),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = Color.White, fontFamily = FontFamily(
                                    Font(
                                        R.font.poppins_medium, weight = FontWeight.Medium
                                    )
                                ), textAlign = TextAlign.Center
                            )
                        )
                        Text(
                            text = "Body Damage: ${it.bodyDamage}",
                            modifier = Modifier
                                .align(Alignment.Start)
                                .padding(horizontal = 24.dp),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = Color.White, fontFamily = FontFamily(
                                    Font(
                                        R.font.poppins_medium, weight = FontWeight.Medium
                                    )
                                ), textAlign = TextAlign.Center
                            )
                        )
                        Text(
                            text = "Leg Damage: ${it.legDamage}",
                            modifier = Modifier
                                .align(Alignment.Start)
                                .padding(horizontal = 24.dp),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = Color.White, fontFamily = FontFamily(
                                    Font(
                                        R.font.poppins_medium, weight = FontWeight.Medium
                                    )
                                ), textAlign = TextAlign.Center
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Skins",
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(horizontal = 8.dp),
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = Color.White, fontFamily = FontFamily(
                                Font(
                                    R.font.poppins_sb, weight = FontWeight.SemiBold
                                )
                            ), textAlign = TextAlign.Center
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    LazyRow {
                        items(weapon.skins) {
                            if (it.displayIcon != null) {
                                Column(horizontalAlignment = CenterHorizontally) {
                                    AsyncImage(
                                        model = it.displayIcon,
                                        contentDescription = null
                                    )
                                    Spacer(modifier = Modifier.height(3.dp))
                                    Text(
                                        text = it.displayName,
                                        style = MaterialTheme.typography.bodyLarge.copy(
                                            color = Color.White, fontFamily = FontFamily(
                                                Font(
                                                    R.font.poppins_sb, weight = FontWeight.SemiBold
                                                )
                                            ), textAlign = TextAlign.Center
                                        ),
                                        modifier = Modifier.padding(horizontal = 8.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.width(16.dp))
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}
