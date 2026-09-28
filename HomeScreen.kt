package com.proyecto.apprickmorty.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.proyecto.apprickmorty.ui.components.EpisodeCard
import com.proyecto.apprickmorty.ui.components.RingAvatar
import com.proyecto.apprickmorty.ui.components.ScannerInputBox
import com.proyecto.apprickmorty.ui.components.StatusBadge
import com.proyecto.apprickmorty.ui.theme.ApprickmortyTheme
import com.proyecto.apprickmorty.ui.theme.Background
import com.proyecto.apprickmorty.ui.theme.CyanAccent
import com.proyecto.apprickmorty.ui.theme.NeonGreen
import com.proyecto.apprickmorty.ui.theme.RickverseSpacing
import com.proyecto.apprickmorty.ui.theme.RickverseType
import com.proyecto.apprickmorty.ui.theme.SurfaceCard
import com.proyecto.apprickmorty.ui.theme.TextSecondary
import com.proyecto.apprickmorty.ui.viewmodel.CharacterViewModel

private data class CastMember(val id: Int, val name: String)
private data class ChronicleEntry(val code: String, val title: String, val date: String)

private val dimensionalCast = listOf(
    CastMember(1, "Rick"),
    CastMember(2, "Morty"),
    CastMember(3, "Summer"),
    CastMember(4, "Beth")
)

private val chronicles = listOf(
    ChronicleEntry("S01E01", "Pilot", "December 2, 2013"),
    ChronicleEntry("S01E02", "Lawnmower Dog", "December 9, 2013"),
    ChronicleEntry("S01E03", "Anatomy Park", "December 16, 2013"),
    ChronicleEntry("S01E04", "M. Night Shaym-Aliens!", "January 13, 2014")
)

@Composable
fun HomeScreen(
    viewModel: CharacterViewModel,
    onCastMemberClick: (Int) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }

    LaunchedEffect(searchQuery) {
        viewModel.loadCharacters(searchQuery)
    }

    val characters = viewModel.characters
    val isLoading = viewModel.isLoading
    val errorMessage = viewModel.errorMessage
    val filteredCharacters = if (searchQuery.isBlank()) {
        characters
    } else {
        characters.filter { it.name.contains(searchQuery, ignoreCase = true) }
    }

    Scaffold(
        containerColor = Background,
        bottomBar = { RickverseBottomBar() }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Background)
                .padding(innerPadding)
                .padding(horizontal = RickverseSpacing.ScreenPaddingHorizontal),
            contentPadding = PaddingValues(top = 20.dp, bottom = 24.dp)
        ) {
            item {
                Text("SCANNING HOST SYSTEM...", style = RickverseType.LabelSmall)
                Spacer(Modifier.height(6.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "WELCOME, MORTAL",
                        style = RickverseType.ScreenTitle.copy(fontWeight = FontWeight.ExtraBold)
                    )
                    StatusBadge("ONLINE")
                }
                Spacer(Modifier.height(16.dp))
                ScannerInputBox(placeholder = "Dimension Scanner...", trailingLabel = "SYS.C137")
                Spacer(Modifier.height(12.dp))
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    singleLine = true,
                    label = { Text("Buscar personaje") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(RickverseSpacing.SectionSpacing))

                SectionHeader(title = "DIMENSIONAL CAST", action = "VIEW ALL")
                Spacer(Modifier.height(12.dp))
            }

            if (isLoading) {
                item {
                    CircularProgressIndicator(
                        color = NeonGreen,
                        modifier = Modifier.padding(vertical = 12.dp)
                    )
                }
            }

            if (!errorMessage.isNullOrBlank()) {
                item {
                    Text(
                        text = "Error: $errorMessage",
                        color = androidx.compose.ui.graphics.Color.Red,
                        style = RickverseType.BodySecondary,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }

            if (filteredCharacters.isNotEmpty()) {
                item {
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(18.dp)) {
                        items(filteredCharacters) { character ->
                            RingAvatar(
                                imageUrl = character.image,
                                name = character.name,
                                isSelected = character.id == 1,
                                onClick = { onCastMemberClick(character.id) },
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                        }
                    }
                    Spacer(Modifier.height(RickverseSpacing.SectionSpacing))
                    SectionHeader(title = "CHRONICLES", action = "NEWEST")
                    Spacer(Modifier.height(12.dp))
                }
            } else if (!isLoading) {
                item {
                    Text(
                        text = "No se encontraron personajes para esta búsqueda.",
                        style = RickverseType.BodySecondary,
                        color = TextSecondary,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }

            items(chronicles) { entry ->
                EpisodeCard(
                    code = entry.code,
                    title = entry.title,
                    subtitle = entry.date,
                    onClick = { /* detalle de episodio pendiente de conectar */ },
                    modifier = Modifier.padding(bottom = RickverseSpacing.CardSpacing)
                )
            }
        }
    }
}

@Composable
private fun SectionHeader(title: String, action: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(title, style = RickverseType.SectionHeader)
        Text(action, style = RickverseType.LabelSmall, color = CyanAccent)
    }
}

@Composable
private fun RickverseBottomBar() {
    NavigationBar(containerColor = SurfaceCard) {
        val items = listOf(
            Triple("Home", Icons.Default.Home, true),
            Triple("Cast", Icons.Default.People, false),
            Triple("Episodes", Icons.Default.Videocam, false),
            Triple("Portals", Icons.Default.LocationOn, false),
            Triple("System", Icons.Default.Settings, false)
        )
        items.forEach { (label, icon, selected) ->
            NavigationBarItem(
                selected = selected,
                onClick = { /* navegación de tabs pendiente de conectar */ },
                icon = { Icon(icon, contentDescription = label) },
                label = { Text(label) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = NeonGreen,
                    selectedTextColor = NeonGreen,
                    unselectedIconColor = TextSecondary,
                    unselectedTextColor = TextSecondary,
                    indicatorColor = Background
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    ApprickmortyTheme {
        HomeScreen(
            viewModel = CharacterViewModel(),
            onCastMemberClick = {}
        )
    }
}
