package com.example.tutorapp.ui.screen.mapas.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.tutorapp.R
import com.example.tutorapp.ui.theme.PrincipalAqua
import com.example.tutorapp.ui.theme.PrincipalGris

@Composable
fun BusquedaBar(
    textoBusqueda: String,
    onClick: () -> Unit,
    onClickMic: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .border(BorderStroke(1.dp, PrincipalAqua), shape = MaterialTheme.shapes.medium)
            .background(Color.White, shape = MaterialTheme.shapes.medium)
            .clickable { onClick() }
            .padding(horizontal = 12.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Buscar",
                tint = PrincipalGris,
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterStart) {
                Text(
                    text = if (textoBusqueda.isEmpty()) "Buscar..." else textoBusqueda,
                    color = if (textoBusqueda.isEmpty()) Color.Gray else Color.Black
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Icon(
                painter = painterResource(id = R.drawable.ic_outline_mic),
                contentDescription = "Buscar por voz",
                tint = PrincipalGris,
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onClickMic() }
            )
        }
    }
}
