package com.umain.seatmap.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FlightTakeoff
import androidx.compose.material.icons.rounded.Wallet
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

/**
 * The final screen shown after confirmation.
 * Displays flight details and QR code in a split-layout ticket style.
 */

@Composable
fun BoardingPassTicket(seat: PlaneSeat) {
    var headerVisible by remember { mutableStateOf(false) }
    var bodyVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        headerVisible = true
        delay(250)
        bodyVisible = true
    }

    val fluidEasing = FastOutSlowInEasing

    Column(modifier = Modifier.fillMaxSize()) {
        AnimatedVisibility(
            visible = headerVisible,
            enter =
                slideInVertically(
                    initialOffsetY = { -it },
                    animationSpec = tween(durationMillis = 800, easing = fluidEasing),
                ),
            modifier = Modifier.fillMaxWidth(),
        ) {
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(420.dp)
                        .background(
                            Brush.verticalGradient(
                                listOf(Color(0xFF0F172A), Color(0xFF1E1B4B)),
                            ),
                            shape = RectangleShape,
                        ),
            ) {
                Canvas(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .graphicsLayer { alpha = 0.05f },
                ) {
                    val space = 40f
                    for (x in 0..(size.width / space).toInt()) {
                        for (y in 0..(size.height / space).toInt()) {
                            drawCircle(
                                Color.White,
                                radius = 2f,
                                center = Offset(x * space, y * space),
                            )
                        }
                    }
                }

                Column(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .windowInsetsPadding(WindowInsets.statusBars)
                            .padding(horizontal = 32.dp, vertical = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly,
                ) {
                    AnimatedVisibility(
                        visible = headerVisible,
                        enter = fadeIn(tween(600, delayMillis = 200)),
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                        ) {
                            Text(
                                "LHR",
                                fontSize = 42.sp,
                                fontWeight = FontWeight.Black,
                                color = Color.White,
                            )
                            Spacer(Modifier.width(24.dp))
                            Icon(
                                Icons.Rounded.FlightTakeoff,
                                null,
                                tint = Color(0xFFA78BFA),
                                modifier = Modifier.size(36.dp),
                            )
                            Spacer(Modifier.width(24.dp))
                            Text(
                                "HND",
                                fontSize = 42.sp,
                                fontWeight = FontWeight.Black,
                                color = Color.White,
                            )
                        }
                    }

                    AnimatedVisibility(
                        visible = headerVisible,
                        enter = fadeIn(tween(600, delayMillis = 400)),
                    ) {
                        Text(
                            "Mar 16, 2026 • 07h 45m",
                            color = Color(0xFF94A3B8),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium,
                        )
                    }

                    Spacer(Modifier.height(8.dp))

                    AnimatedVisibility(
                        visible = headerVisible,
                        enter =
                            fadeIn(tween(600, delayMillis = 500)) +
                                    slideInVertically(
                                        tween(
                                            600,
                                            delayMillis = 500,
                                        ),
                                    ) { 20 },
                    ) {
                        HorizontalDivider(
                            color = Color(0xFF334155).copy(alpha = 0.5f),
                        )
                    }

                    Spacer(Modifier.height(8.dp))

                    AnimatedVisibility(
                        visible = headerVisible,
                        enter =
                            fadeIn(tween(600, delayMillis = 600)) +
                                    slideInVertically(
                                        tween(
                                            600,
                                            delayMillis = 600,
                                        ),
                                    ) { 20 },
                    ) {
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            DetailColumnLarge("Flight", "JM44", Alignment.Start)
                            DetailColumnLarge(
                                "Class",
                                if (seat.type == SeatClass.ECONOMY_PLUS) "Eco +" else "Eco",
                                Alignment.CenterHorizontally,
                            )
                            DetailColumnLarge("Seat", seat.label, Alignment.End)
                        }
                    }

                    AnimatedVisibility(
                        visible = headerVisible,
                        enter =
                            fadeIn(tween(600, delayMillis = 800)) +
                                    slideInVertically(
                                        tween(
                                            600,
                                            delayMillis = 800,
                                        ),
                                    ) { 20 },
                    ) {
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            DetailColumnLarge("Passenger", "Jackson M.", Alignment.Start)
                            DetailColumnLarge("Gate", "22", Alignment.CenterHorizontally)
                            DetailColumnLarge("Time", "10:40", Alignment.End)
                        }
                    }
                    Spacer(Modifier.height(12.dp))
                }
            }
        }

        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .weight(1f),
        ) {
            androidx.compose.animation.AnimatedVisibility(
                visible = bodyVisible,
                enter =
                    slideInVertically(
                        initialOffsetY = { 200 },
                        animationSpec = tween(durationMillis = 800, easing = fluidEasing),
                    ) + fadeIn(tween(500)),
                modifier = Modifier.fillMaxSize(),
            ) {
                Column(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .padding(horizontal = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Spacer(Modifier.weight(1f))

                    Box(
                        modifier =
                            Modifier
                                .size(240.dp)
                                .shadow(24.dp, RoundedCornerShape(32.dp), spotColor = Color(0xFFCBD5E1))
                                .clip(RoundedCornerShape(32.dp))
                                .background(Color.White)
                                .padding(8.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        GenerativeGradientQrCode()
                        Box(
                            modifier =
                                Modifier
                                    .size(48.dp)
                                    .clip(CircleShape)
                                    .background(Color.White),
                            contentAlignment = Alignment.Center,
                        ) {
                            Icon(
                                Icons.Rounded.FlightTakeoff,
                                null,
                                tint = Color.Black,
                                modifier = Modifier.size(28.dp),
                            )
                        }
                    }

                    Spacer(Modifier.weight(1f))

                    val interactionSource = remember { MutableInteractionSource() }
                    val isPressed by interactionSource.collectIsPressedAsState()
                    val buttonScale by animateFloatAsState(targetValue = if (isPressed) 0.95f else 1f)

                    Button(
                        onClick = {},
                        interactionSource = interactionSource,
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .height(64.dp)
                                .graphicsLayer {
                                    scaleX = buttonScale
                                    scaleY = buttonScale
                                }.shadow(
                                    16.dp,
                                    RoundedCornerShape(20.dp),
                                    spotColor = Color(0xFF4F46E5),
                                ),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                        contentPadding =
                            androidx.compose.foundation.layout
                                .PaddingValues(0.dp),
                        shape = RoundedCornerShape(20.dp),
                    ) {
                        Box(
                            modifier =
                                Modifier
                                    .fillMaxSize()
                                    .background(
                                        Brush.horizontalGradient(
                                            listOf(Color(0xFF4F46E5), Color(0xFF06B6D4)),
                                        ),
                                    ),
                            contentAlignment = Alignment.Center,
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Rounded.Wallet, null, tint = Color.White)
                                Spacer(Modifier.width(12.dp))
                                Text(
                                    "Add to Wallet",
                                    color = Color.White,
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.Bold,
                                )
                            }
                        }
                    }

                    Spacer(
                        Modifier
                            .navigationBarsPadding()
                            .height(48.dp),
                    )
                }
            }
        }
    }
}