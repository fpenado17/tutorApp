package com.example.tutorapp.ui.screen.procesos.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.tutorapp.ui.theme.PrincipalGris
import com.example.tutorapp.ui.theme.RojoUES
import com.example.tutorapp.ui.theme.SecundarioGris

@Composable
fun <T> SimpleList(
    items: List<T>,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    timeoutReached: Boolean = false,
    emptyMessage: String = "No hay datos.",
    onItemClick: (T) -> Unit = {},
    itemContent: @Composable (T) -> Unit
) {
    when {
        isLoading -> {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        timeoutReached -> {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = emptyMessage,
                    color = RojoUES,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
        items.isEmpty() -> {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = emptyMessage,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
        else -> {
            LazyColumn(modifier = modifier) {
                itemsIndexed(items) { index, item ->
                    val backgroundColor = if (index % 2 == 0) PrincipalGris else SecundarioGris

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp)
                            .clickable { onItemClick(item) }
                            .clip(RoundedCornerShape(12.dp))
                            .background(backgroundColor)
                            .padding(16.dp)
                    ) {
                        itemContent(item)
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

