package com.umain.seatmap.ui

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlin.random.Random

@Composable
fun GenerativeGradientQrCode() {
    val brush =
        Brush.linearGradient(
            colors = listOf(Color(0xFF2E1065), Color(0xFF3B82F6), Color(0xFF06B6D4)),
            start = Offset.Zero,
            end = Offset(500f, 500f),
        )

    Spacer(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(12.dp)
                .drawWithCache {
                    val gridSize = 25
                    val cellSize = size.width / gridSize
                    val r = Random(12345)

                    val gridPoints = mutableListOf<Offset>()
                    for (row in 0 until gridSize) {
                        for (col in 0 until gridSize) {
                            val inTopLeft = row < 8 && col < 8
                            val inTopRight = row < 8 && col >= gridSize - 8
                            val inBottomLeft = row >= gridSize - 8 && col < 8

                            if (!inTopLeft && !inTopRight && !inBottomLeft) {
                                if (r.nextBoolean()) {
                                    gridPoints.add(Offset(col * cellSize + 1.5f, row * cellSize + 1.5f))
                                }
                            }
                        }
                    }

                    onDrawBehind {
                        fun drawEye(
                            row: Int,
                            col: Int,
                        ) {
                            val offset = Offset(col * cellSize, row * cellSize)
                            val size = 7 * cellSize
                            drawRoundRect(
                                brush = brush,
                                topLeft = offset,
                                size = Size(size, size),
                                cornerRadius = CornerRadius(16f),
                            )
                            drawRect(
                                Color.White,
                                topLeft = offset + Offset(cellSize, cellSize),
                                size = Size(size - 2 * cellSize, size - 2 * cellSize),
                            )
                            drawRoundRect(
                                brush = brush,
                                topLeft = offset + Offset(2 * cellSize, 2 * cellSize),
                                size = Size(size - 4 * cellSize, size - 4 * cellSize),
                                cornerRadius = CornerRadius(8f),
                            )
                        }
                        drawEye(0, 0)
                        drawEye(0, gridSize - 7)
                        drawEye(gridSize - 7, 0)

                        gridPoints.forEach { point ->
                            drawRoundRect(
                                brush = brush,
                                topLeft = point,
                                size = Size(cellSize - 3f, cellSize - 3f),
                                cornerRadius = CornerRadius(4f),
                            )
                        }
                    }
                },
    )
}