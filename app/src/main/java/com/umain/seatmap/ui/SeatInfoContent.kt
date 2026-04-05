package com.umain.seatmap.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AirlineSeatLegroomExtra
import androidx.compose.material.icons.rounded.Bolt
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Wifi
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SeatInfoContent(
    seat: PlaneSeat,
    onClose: () -> Unit,
    onConfirm: () -> Unit,
) {
    var contentVisible by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val haptic = LocalHapticFeedback.current

    LaunchedEffect(Unit) {
        contentVisible = true
    }

    fun performExit(afterExit: () -> Unit) {
        contentVisible = false
        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
        scope.launch {
            delay(400)
            afterExit()
        }
    }

    Column(Modifier.padding(32.dp)) {
        AnimatableItem(visible = contentVisible, enterDelay = 0, exitDelay = 200) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier =
                            Modifier
                                .size(64.dp)
                                .clip(RoundedCornerShape(20.dp))
                                .background(
                                    Brush.linearGradient(
                                        listOf(
                                            Color(0xFF2E1065),
                                            Color(0xFF3B82F6),
                                        ),
                                    ),
                                ),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            seat.label,
                            color = Color.White,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Black,
                        )
                    }
                    Spacer(Modifier.width(20.dp))
                    Column {
                        Text(
                            if (seat.type == SeatClass.ECONOMY_PLUS) "Economy +" else "Economy",
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontSize = 22.sp,
                        )
                        Text(
                            "Boeing 787-9",
                            color = Color(0xFF94A3B8),
                            fontWeight = FontWeight.Medium,
                            fontSize = 15.sp,
                        )
                    }
                }
                IconButton(
                    onClick = { performExit(onClose) },
                    modifier =
                        Modifier
                            .size(44.dp)
                            .border(1.dp, Color(0xFF334155), CircleShape)
                            .background(Color(0xFF1E293B), CircleShape),
                ) {
                    Icon(
                        Icons.Rounded.Close,
                        null,
                        tint = Color.White,
                        modifier = Modifier.size(22.dp),
                    )
                }
            }
        }

        Spacer(Modifier.height(36.dp))

        AnimatableItem(visible = contentVisible, enterDelay = 100, exitDelay = 150) {
            HorizontalDivider(color = Color(0xFF334155))
        }

        Spacer(Modifier.height(36.dp))

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            AnimatableItem(visible = contentVisible, enterDelay = 150, exitDelay = 100) {
                InfoItem(Icons.Rounded.AirlineSeatLegroomExtra, "Legroom", seat.legroom)
            }
            AnimatableItem(visible = contentVisible, enterDelay = 250, exitDelay = 100) {
                InfoItem(Icons.Rounded.Bolt, "Power", "USB-C")
            }
            AnimatableItem(visible = contentVisible, enterDelay = 350, exitDelay = 100) {
                InfoItem(Icons.Rounded.Wifi, "Wi-Fi", "Starlink")
            }
        }

        Spacer(Modifier.height(48.dp))

        AnimatableItem(visible = contentVisible, enterDelay = 450, exitDelay = 0) {
            Button(
                onClick = { performExit(onConfirm) },
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(64.dp)
                        .shadow(16.dp, RoundedCornerShape(20.dp), spotColor = Color(0xFF4F46E5)),
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
                                    listOf(
                                        Color(0xFF4F46E5),
                                        Color(0xFF06B6D4),
                                    ),
                                ),
                            ),
                    contentAlignment = Alignment.Center,
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            "Confirm Selection ",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                        )
                        Text(
                            "$${seat.price}",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.White.copy(alpha = 0.9f),
                        )
                    }
                }
            }
        }
    }
}
