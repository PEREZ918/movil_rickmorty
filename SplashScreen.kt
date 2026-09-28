package com.proyecto.apprickmorty.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.proyecto.apprickmorty.ui.components.NeonProgressBar
import com.proyecto.apprickmorty.ui.theme.Background
import com.proyecto.apprickmorty.ui.theme.CyanAccent
import com.proyecto.apprickmorty.ui.theme.NeonGreen
import com.proyecto.apprickmorty.ui.theme.RickverseSpacing
import com.proyecto.apprickmorty.ui.theme.RickverseType
import com.proyecto.apprickmorty.ui.theme.TextSecondary
import androidx.compose.ui.tooling.preview.Preview
import com.proyecto.apprickmorty.ui.theme.ApprickmortyTheme
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onFinished: () -> Unit
) {
    val progress = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        progress.animateTo(0.89f, animationSpec = tween(durationMillis = 1800))
        delay(400)
        onFinished()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .padding(horizontal = RickverseSpacing.ScreenPaddingHorizontal, vertical = 20.dp)
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("DIMENSION: C-137", style = RickverseType.LabelSmall)
            Text("FLUID LVL: 100%", style = RickverseType.LabelSmall, color = NeonGreen)
        }

        // Portal + título centrados
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            PortalSwirl(size = 260.dp)
            Spacer(Modifier.height(28.dp))
            Text(
                "RICKVERSE",
                style = RickverseType.DisplayTitle.copy(fontSize = 30.sp, textAlign = TextAlign.Center)
            )
            Spacer(Modifier.height(4.dp))
            Text(
                "MULTIVERSE HUB",
                style = RickverseType.LabelSmall.copy(color = TextSecondary, fontWeight = FontWeight.SemiBold)
            )
        }

        // Progreso
        Column(Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("CALIBRATING PORTAL FLUID...", style = RickverseType.LabelSmall)
                Text("${(progress.value * 100).toInt()}%", style = RickverseType.LabelSmall, color = NeonGreen)
            }
            Spacer(Modifier.height(8.dp))
            NeonProgressBar(progress = progress.value, modifier = Modifier.fillMaxWidth())
        }
    }
}

@Composable
private fun PortalSwirl(size: androidx.compose.ui.unit.Dp) {
    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(
                Brush.radialGradient(
                    colors = listOf(
                        CyanAccent.copy(alpha = 0.9f),
                        NeonGreen.copy(alpha = 0.5f),
                        Background
                    ),
                    center = Offset.Unspecified
                )
            )
    ) {
        Box(
            modifier = Modifier
                .size(size * 0.62f)
                .align(Alignment.Center)
                .clip(CircleShape)
                .background(
                    Brush.sweepGradient(
                        colors = listOf(CyanAccent, NeonGreen, CyanAccent, Background, CyanAccent)
                    )
                )
        )
        Box(
            modifier = Modifier
                .size(size * 0.30f)
                .align(Alignment.Center)
                .clip(CircleShape)
                .background(Background)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    ApprickmortyTheme {
        SplashScreen(onFinished = {})
    }
}
