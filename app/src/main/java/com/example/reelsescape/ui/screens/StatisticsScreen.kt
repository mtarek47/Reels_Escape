package com.example.reelsescape.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*
import com.example.reelsescape.ui.viewmodel.MainViewModel

@Composable
fun StatisticsScreen(viewModel: MainViewModel) {
    val todayCount = viewModel.todayEscapes.observeAsState(0)
    val weeklyCount = viewModel.weeklyEscapes.observeAsState(0)
    val monthlyCount = viewModel.monthlyEscapes.observeAsState(0)
    val totalCount = viewModel.totalEscapes.observeAsState(0)

    val youtubeTotal = viewModel.youtubeTotalEscapes.observeAsState(0)
    val instagramTotal = viewModel.instagramTotalEscapes.observeAsState(0)
    val facebookTotal = viewModel.facebookTotalEscapes.observeAsState(0)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 20.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 100.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "FOCUS METRICS",
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.5.sp,
                    color = PrimaryAccent
                ),
                modifier = Modifier.padding(start = 4.dp, top = 8.dp)
            )
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatTile(title = "Today", count = todayCount.value, modifier = Modifier.weight(1f))
                StatTile(title = "This Week", count = weeklyCount.value, modifier = Modifier.weight(1f))
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatTile(title = "This Month", count = monthlyCount.value, modifier = Modifier.weight(1f))
                StatTile(title = "All Time", count = totalCount.value, modifier = Modifier.weight(1f))
            }
        }

        item {
            Text(
                text = "BREAKDOWN BY APP",
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.5.sp,
                    color = PrimaryAccent
                ),
                modifier = Modifier.padding(start = 4.dp, top = 12.dp)
            )
        }

        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = SurfaceDark),
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, CardBorderDark, RoundedCornerShape(24.dp))
                    .testTag("app_breakdown_card")
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    AppBreakdownRow(name = "YouTube Shorts", total = youtubeTotal.value, color = YouTubeRed)
                    AppBreakdownRow(name = "Instagram Reels", total = instagramTotal.value, color = InstagramPurple)
                    AppBreakdownRow(name = "Facebook Reels", total = facebookTotal.value, color = FacebookBlue)
                }
            }
        }
    }
}

@Composable
fun StatTile(title: String, count: Int, modifier: Modifier = Modifier) {
    Card(
        colors = CardDefaults.cardColors(containerColor = SurfaceDark),
        shape = RoundedCornerShape(20.dp),
        modifier = modifier.border(1.dp, CardBorderDark, RoundedCornerShape(20.dp))
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall.copy(color = TextSecondaryDark, fontWeight = FontWeight.Medium)
            )
            Text(
                text = count.toString(),
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Light,
                    color = PrimaryAccent
                )
            )
        }
    }
}

@Composable
fun AppBreakdownRow(name: String, total: Int, color: Color) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .background(color, shape = RoundedCornerShape(3.dp))
            )
            Text(
                text = name,
                style = MaterialTheme.typography.bodyMedium.copy(color = TextPrimaryDark, fontWeight = FontWeight.Medium)
            )
        }

        Text(
            text = "$total escapes",
            style = MaterialTheme.typography.labelLarge.copy(color = TextSecondaryDark, fontWeight = FontWeight.Bold)
        )
    }
}
