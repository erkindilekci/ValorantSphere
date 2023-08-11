package com.erkindilekci.valorantsphere.presentation.screens.weaponsscreen

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.erkindilekci.valorantsphere.R
import com.erkindilekci.valorantsphere.data.remote.dto.weapon.Weapon
import com.erkindilekci.valorantsphere.presentation.util.Drawer
import com.erkindilekci.valorantsphere.util.navigation.Screen
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch
import java.util.Locale

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WeaponsScreen(
    navController: NavController,
    viewModel: WeaponsScreenViewModel = hiltViewModel()
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
        MaterialTheme.colorScheme.primary,
        MaterialTheme.colorScheme.background
    )

    val systemUiController = rememberSystemUiController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val statusColor = MaterialTheme.colorScheme.primary

    SideEffect {
        systemUiController.setStatusBarColor(statusColor)
    }

    Scaffold {
        Box(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(brush = Brush.verticalGradient(colors = colors))
            ) {}

            LottieAnimation(
                modifier = Modifier.alpha(0.4f),
                composition = composition, progress = { animationState },
                contentScale = ContentScale.Fit
            )
        }
        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        if (!state.isLoading && state.weapons.isNotEmpty()) {

            Drawer(
                drawerState = drawerState,
                onAgentClick = {
                    navController.popBackStack()
                    navController.navigate(Screen.AgentsScreen.route)
                },
                onWeaponClick = { scope.launch { drawerState.close() } },
                onGameModClick = {
                    navController.popBackStack()
                    navController.navigate(Screen.GameModesScreen.route)
                },
                onMapsClick = {
                    navController.popBackStack()
                    navController.navigate(Screen.MapsScreen.route)
                }
            ) {
                Column(
                    modifier = Modifier
                        .padding(it)
                        .fillMaxSize()
                ) {
                    WeaponPager(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxSize()
                            .padding(bottom = 8.dp),
                        listState = agentViewerState,
                        items = state.weapons,
                        onItemClick = { id ->
                            navController.navigate(
                                Screen.WeaponDetailsScreen.passId(
                                    id
                                )
                            )
                        },
                        onMenuClick = {
                            scope.launch {
                                drawerState.open()
                            }
                        }
                    )

                    WeaponPicker(items = state.weapons, pickerState)
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WeaponPager(
    modifier: Modifier,
    listState: PagerState,
    items: List<Weapon>,
    onItemClick: (String) -> Unit,
    onMenuClick: () -> Unit
) {
    HorizontalPager(modifier = modifier, state = listState, pageCount = Int.MAX_VALUE) {
        val pageOffset = (listState.currentPage - it) + listState.currentPageOffsetFraction

        val textAlpha by animateFloatAsState(
            targetValue = if (pageOffset != 0.0f) 0.0f else 1f,
            tween(delayMillis = 300), label = ""
        )

        val textSize by animateFloatAsState(
            targetValue = if (pageOffset != 0.0f) 0.0f else 1f, animationSpec = tween(300),
            label = ""
        )

        val boxSize by animateFloatAsState(
            targetValue = if (pageOffset != 0.0f) 0.75f else 1f,
            animationSpec = tween(300), label = ""
        )

        val index = it % items.size

        Column(modifier = Modifier
            .fillMaxSize()
            .clickable { onItemClick(items[index].uuid) }
        ) {
            Text(
                text = items[index].displayName.uppercase(locale = Locale.getDefault()),
                modifier = Modifier
                    .fillMaxWidth()
                    .alpha(textAlpha)
                    .padding(top = 24.dp)
                    .graphicsLayer {
                        scaleY = textSize
                        scaleX = textSize
                    },
                style = MaterialTheme.typography.displaySmall.copy(
                    color = Color.White,
                    fontFamily = FontFamily(
                        Font(
                            R.font.valorant_font,
                            weight = FontWeight.Bold
                        )
                    ),
                    textAlign = TextAlign.Center
                )
            )

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .graphicsLayer(
                        scaleX = boxSize,
                        scaleY = boxSize
                    ),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = items[index].displayIcon,
                    contentDescription = "",
                    contentScale = ContentScale.FillBounds
                )
            }
        }

        Box(modifier = modifier.fillMaxSize()) {
            IconButton(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .size(75.dp)
                    .alpha(textAlpha)
                    .graphicsLayer {
                        scaleY = textSize
                        scaleX = textSize
                    },
                onClick = onMenuClick
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .size(48.dp)
                        .padding(5.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WeaponPicker(items: List<Weapon>, listState: PagerState) {
    val scope = rememberCoroutineScope()
    val screenWidth = LocalConfiguration.current.screenWidthDp / 3

    fun onTop(index: Int) {
        scope.launch {
            listState.animateScrollToPage(index)
        }
    }

    HorizontalPager(
        pageCount = Int.MAX_VALUE,
        state = listState,
        contentPadding = PaddingValues(horizontal = screenWidth.dp),
        modifier = Modifier.padding(bottom = 8.dp)
    ) {
        val pageOffset = (listState.currentPage - it) + listState.currentPageOffsetFraction
        val boxSize = 80f

        val sizeInside by animateFloatAsState(
            targetValue = if (pageOffset == 0f) boxSize else (boxSize / 1.2).toFloat(),
            animationSpec = tween(easing = LinearOutSlowInEasing), label = ""
        )
        val borderColor by animateColorAsState(
            targetValue = if (pageOffset == 0f) MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
            else MaterialTheme.colorScheme.background,
            animationSpec = tween(easing = LinearOutSlowInEasing), label = ""
        )
        val borderWidth by animateIntAsState(
            targetValue = if (pageOffset == 0f) 1 else 0,
            animationSpec = tween(easing = LinearOutSlowInEasing), label = ""
        )

        val index = it % items.size

        Box(
            modifier = Modifier.size(boxSize.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(sizeInside.dp)
                    .background(color = Color(0xFF183040))
                    .border(width = borderWidth.dp, color = borderColor)
                    .clickable { onTop(it) },
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = items[index].displayIcon,
                    modifier = Modifier
                        .size(sizeInside.dp)
                        .alpha(if (it == listState.currentPage) 1f else 0.6f),
                    contentScale = ContentScale.Fit,
                    contentDescription = null
                )
            }
        }
    }
}
