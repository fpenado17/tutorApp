package com.example.tutorapp.ui.screen.inicio.components

import CarouselItem
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TarjetasCarrusel(items: List<CarouselItem>) {
    val pagerState = rememberPagerState()

    HorizontalPager(
        count = items.size,
        state = pagerState,
        contentPadding = PaddingValues(horizontal = 32.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
    ) { page ->
        val item = items[page]
        Card(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxHeight()
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFD32F2F))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(item.title, style = MaterialTheme.typography.titleMedium, color = Color.White)
                Spacer(modifier = Modifier.height(8.dp))
                Text(item.description, style = MaterialTheme.typography.bodyMedium, color = Color.White)
            }
        }
    }
}