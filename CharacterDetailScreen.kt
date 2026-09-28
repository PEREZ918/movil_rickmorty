package com.proyecto.apprickmorty.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Public
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.proyecto.apprickmorty.data.model.RickCharacter
import com.proyecto.apprickmorty.ui.components.EpisodeCard
import com.proyecto.apprickmorty.ui.components.InfoRowCard
import com.proyecto.apprickmorty.ui.components.StatusBadge
import com.proyecto.apprickmorty.ui.theme.Background
import com.proyecto.apprickmorty.ui.theme.NeonGreen
import com.proyecto.apprickmorty.ui.theme.RickverseSpacing
import com.proyecto.apprickmorty.ui.theme.RickverseType
import com.proyecto.apprickmorty.ui.theme.TextSecondary
import com.proyecto.apprickmorty.ui.viewmodel.CharacterViewModel

// Placeholder: en la app real vendría del endpoint /episode del personaje
private val transmissionLogs = listOf(
    Triple("S01E01", "Pilot", ""),
    Triple("S01E06", "Rick Potion No. 9", ""),
    Triple("S02E04", "Total Rickall", "")
)

@Composable
fun CharacterDetailScreen(
    characterId: Int,
    onBack: () -> Unit,
    viewModel: CharacterViewModel
) {
    LaunchedEffect(characterId) {
        viewModel.loadCharacterById(characterId)
    }

    val character = viewModel.selectedCharacter
    val errorMessage = viewModel.errorMessage

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {
        when {
            character != null -> {
                CharacterDetailContent(character = character, onBack = onBack)
            }
            viewModel.isLoading -> {
                CircularProgressIndicator(
                    color = NeonGreen,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            else -> {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = errorMessage ?: "No se pudo cargar el personaje.",
                        style = RickverseType.BodyPrimary,
                        color = TextSecondary
                    )
                    Spacer(Modifier.height(12.dp))
                    androidx.compose.material3.Button(
                        onClick = { viewModel.loadCharacterById(characterId) }
                    ) {
                        Text("Reintentar")
                    }
                }
            }
        }
    }
}

@Composable
private fun CharacterDetailContent(character: RickCharacter, onBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Box {
            AsyncImage(
                model = character.image,
                contentDescription = character.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(360.dp)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(360.dp)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                androidx.compose.ui.graphics.Color.Transparent,
                                Background
                            ),
                            startY = 200f
                        )
                    )
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween
            ) {
                CircleIconButton(icon = Icons.Default.ArrowBack, onClick = onBack)
                CircleIconButton(icon = Icons.Default.BookmarkBorder, onClick = { /* guardar, pendiente */ })
            }
        }

        Column(modifier = Modifier.padding(horizontal = RickverseSpacing.ScreenPaddingHorizontal)) {
            Spacer(Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(character.name.uppercase(), style = RickverseType.ScreenTitle)
                StatusBadge(character.status)
            }
            Spacer(Modifier.height(4.dp))
            Text("Species: ${character.species}", style = RickverseType.BodySecondary, color = TextSecondary)

            Spacer(Modifier.height(RickverseSpacing.SectionSpacing))
            InfoRowCard(
                icon = Icons.Default.Public,
                label = "Original Origin",
                value = character.origin.name
            )
            Spacer(Modifier.height(RickverseSpacing.CardSpacing))
            InfoRowCard(
                icon = Icons.Default.LocationOn,
                label = "Last Known Location",
                value = character.location.name
            )

            Spacer(Modifier.height(RickverseSpacing.SectionSpacing))
            Text("TRANSMISSION LOGS", style = RickverseType.SectionHeader)
            Spacer(Modifier.height(12.dp))
            transmissionLogs.forEach { (code, title, subtitle) ->
                EpisodeCard(
                    code = code,
                    title = title,
                    subtitle = subtitle,
                    modifier = Modifier.padding(bottom = RickverseSpacing.CardSpacing)
                )
            }
            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun CircleIconButton(icon: androidx.compose.ui.graphics.vector.ImageVector, onClick: () -> Unit) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(Background.copy(alpha = 0.6f))
    ) {
        Icon(icon, contentDescription = null, tint = androidx.compose.ui.graphics.Color.White)
    }
}
