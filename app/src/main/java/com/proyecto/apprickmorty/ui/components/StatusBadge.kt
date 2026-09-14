package com.proyecto.apprickmorty.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.proyecto.apprickmorty.ui.theme.NeonGreen
import com.proyecto.apprickmorty.ui.theme.NeonGreenDim
import com.proyecto.apprickmorty.ui.theme.PillShape
import com.proyecto.apprickmorty.ui.theme.RickverseType

@Composable
fun StatusBadge(text: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .clip(PillShape)
            .background(NeonGreenDim)
            .border(1.dp, NeonGreen.copy(alpha = 0.4f), PillShape)
            .padding(horizontal = 10.dp, vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        androidx.compose.foundation.layout.Box(
            Modifier
                .size(6.dp)
                .clip(CircleShape)
                .background(NeonGreen)
        )
        Spacer(Modifier.width(6.dp))
        Text(text.uppercase(), style = RickverseType.BadgeText, color = NeonGreen)
    }
}
