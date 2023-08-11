package com.erkindilekci.valorantsphere.presentation.screens.agentdetailsscreen

import android.graphics.Color.*
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
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
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import java.util.Locale

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AgentDetailsScreen(
    navController: NavController,
    viewModel: AgentDetailsScreenViewModel = hiltViewModel()
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

    val systemUiController = rememberSystemUiController()

    var selectedAbilityIndex by remember { mutableStateOf(-1) }

    Scaffold { paddingValues ->
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
            state.agent?.let { agent ->

                val colorList = agent.backgroundGradientColors.map { Color(parseColor("#$it")) }

                SideEffect {
                    systemUiController.setStatusBarColor(colorList[0])
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(brush = Brush.verticalGradient(colorList))
                        .verticalScroll(rememberScrollState())
                ) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Box {
                            AsyncImage(
                                model = agent.fullPortrait,
                                contentDescription = null,
                                modifier = Modifier.size(200.dp).align(Alignment.Center)
                            )

                            IconButton(
                                onClick = { navController.popBackStack() },
                                modifier = Modifier
                                    .padding(8.dp)
                                    .align(Alignment.TopStart)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ArrowBack,
                                    contentDescription = null,
                                    modifier = Modifier.size(30.dp),
                                    tint = Color.White
                                )
                            }
                        }

                        Column {
                            Text(
                                text = agent.displayName.uppercase(Locale.ROOT),
                                modifier = Modifier.padding(top = 32.dp),
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
                            Spacer(modifier = Modifier.height(5.dp))
                            Row {
                                AsyncImage(
                                    model = agent.role.displayIcon,
                                    contentDescription = null,
                                    modifier = Modifier.size(24.dp)
                                )

                                Spacer(modifier = Modifier.width(5.dp))

                                Text(
                                    text = agent.role.displayName,
                                    style = TextStyle(
                                        fontFamily = FontFamily(
                                            Font(R.font.poppins_medium, weight = FontWeight.Medium)
                                        )
                                    ),
                                    color = Color.White,
                                    textAlign = TextAlign.Justify,
                                    fontSize = 18.5.sp
                                )
                            }
                            Text(
                                text = agent.role.description,
                                style = TextStyle(
                                    fontFamily = FontFamily(
                                        Font(R.font.poppins_regular, weight = FontWeight.Normal)
                                    ), fontStyle = FontStyle.Italic
                                ),
                                color = Color.White,
                                modifier = Modifier.padding(end = 8.dp),
                                fontSize = 14.sp
                            )
                        }
                    }

                    Text(
                        text = agent.description,
                        style = TextStyle(
                            fontFamily = FontFamily(
                                Font(R.font.poppins_medium, weight = FontWeight.Medium)
                            )
                        ),
                        color = Color.White,
                        textAlign = TextAlign.Justify,
                        modifier = Modifier.padding(horizontal = 8.dp),
                        fontSize = 15.5.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row {
                        agent.abilities.forEachIndexed { index, ability ->
                            Column(horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { selectedAbilityIndex = index }) {
                                Text(
                                    text = ability.displayName,
                                    color = Color.White,
                                    fontSize = if (ability.displayName.length >= 13) 12.sp else 14.sp,
                                    style = TextStyle(
                                        fontFamily = FontFamily(
                                            Font(R.font.poppins_sb, weight = FontWeight.SemiBold)
                                        )
                                    ),
                                    modifier = Modifier.padding(horizontal = 2.dp),
                                    textAlign = TextAlign.Center
                                )
                                ability.displayIcon?.let {
                                    AsyncImage(
                                        model = it,
                                        contentDescription = ability.displayName,
                                        modifier = Modifier
                                            .size(64.dp)
                                            .padding(horizontal = 2.dp)
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(5.dp))

                    AnimatedVisibility(visible = selectedAbilityIndex != -1) {
                        Text(
                            text = agent.abilities[selectedAbilityIndex].description,
                            style = TextStyle(
                                fontFamily = FontFamily(
                                    Font(R.font.poppins_mi, weight = FontWeight.Medium)
                                )
                            ),
                            color = Color.White,
                            textAlign = TextAlign.Justify,
                            modifier = Modifier.padding(horizontal = 8.dp),
                            fontSize = 14.sp
                        )

                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}
