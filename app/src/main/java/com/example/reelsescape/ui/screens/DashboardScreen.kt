package com.example.reelsescape.ui.screens

import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.reelsescape.database.EscapeEventEntity
import com.example.reelsescape.model.AppSettings
import com.example.reelsescape.ui.viewmodel.MainViewModel
import com.example.ui.theme.*
import com.example.reelsescape.constants.AppConstants
import com.example.reelsescape.ui.components.AppIcon
import com.example.reelsescape.util.AccessibilityUtils
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun DashboardScreen(
    viewModel: MainViewModel,
    isAccessibilityEnabled: Boolean,
    onNavigateToApps: () -> Unit
) {
    val context = LocalContext.current
    val todayEscapes = viewModel.todayEscapes.observeAsState(0)
    val youtubeToday = viewModel.youtubeEscapesToday.observeAsState(0)
    val instagramToday = viewModel.instagramEscapesToday.observeAsState(0)
    val facebookToday = viewModel.facebookEscapesToday.observeAsState(0)
    val recentEventsState = viewModel.recentEvents.observeAsState(emptyList())
    val recentEvents = recentEventsState.value

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 20.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 100.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Status Bar Card
        item {
            StatusCard(
                isAccessibilityEnabled = isAccessibilityEnabled,
                onEnableClick = { AccessibilityUtils.openAccessibilitySettings(context) }
            )
        }

        // Hero Statistics Card
        item {
            HeroStatsCard(
                todayEscapesCount = todayEscapes.value,
                youtubeCount = youtubeToday.value,
                instagramCount = instagramToday.value,
                facebookCount = facebookToday.value
            )
        }

        // Recent Activity Feed
        item {
            Text(
                text = "RECENT ACTIVITY",
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.5.sp,
                    color = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier.padding(start = 4.dp, top = 8.dp)
            )
        }

        if (recentEvents.isEmpty()) {
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No recent escapes. Stay focused!",
                            style = MaterialTheme.typography.bodyMedium.copy(color = TextSecondaryDark)
                        )
                    }
                }
            }
        } else {
            items(recentEvents) { event ->
                RecentEventItem(event)
            }
        }
    }
}

@Composable
fun StatusCard(
    isAccessibilityEnabled: Boolean,
    onEnableClick: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, CardBorderDark, RoundedCornerShape(24.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .clip(CircleShape)
                        .background(if (isAccessibilityEnabled) SystemArmedGreen else Color(0xFFFF5252))
                )
                Column {
                    Text(
                        text = if (isAccessibilityEnabled) "SYSTEM ARMED" else "PROTECTION DISABLED",
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = if (isAccessibilityEnabled) SystemArmedGreen else Color(0xFFFF5252),
                            letterSpacing = 1.2.sp
                        )
                    )
                    Text(
                        text = if (isAccessibilityEnabled) "Accessibility Service Active" else "Permission Required",
                        style = MaterialTheme.typography.bodySmall.copy(color = TextSecondaryDark)
                    )
                }
            }

            if (!isAccessibilityEnabled) {
                Button(
                    onClick = onEnableClick,
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryAccent, contentColor = OnPrimaryAccent),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                    modifier = Modifier.testTag("enable_accessibility_button")
                ) {
                    Text("Enable", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold))
                }
            }
        }
    }
}

@Composable
fun getAppSpecificMockHeights(packageName: String, count: Int): List<Float> {
    if (count == 0) {
        return listOf(0.15f, 0.15f, 0.15f, 0.15f, 0.15f, 0.15f)
    }
    // Base scale factor based on escapes count
    val base = (count.coerceIn(1, 15) / 15f) * 0.7f
    return when (packageName) {
        "com.google.android.youtube" -> listOf(
            0.15f + base * 0.2f,
            0.4f + base * 0.4f,
            0.8f + base * 0.2f,
            0.5f + base * 0.3f,
            0.3f + base * 0.5f,
            0.6f + base * 0.4f
        )
        "com.instagram.android" -> listOf(
            0.3f + base * 0.3f,
            0.6f + base * 0.2f,
            0.2f + base * 0.6f,
            0.7f + base * 0.3f,
            0.9f + base * 0.1f,
            0.5f + base * 0.4f
        )
        else -> listOf(
            0.5f + base * 0.2f,
            0.3f + base * 0.4f,
            0.7f + base * 0.3f,
            0.4f + base * 0.5f,
            0.8f + base * 0.2f,
            0.6f + base * 0.3f
        )
    }
}

@Composable
fun HeroStatsCard(
    todayEscapesCount: Int,
    youtubeCount: Int,
    instagramCount: Int,
    facebookCount: Int
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0x3349454F)),
        shape = RoundedCornerShape(32.dp),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color(0x4D49454F), RoundedCornerShape(32.dp))
            .testTag("hero_stats_card")
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Text(
                text = "Today's Focus Gain",
                style = MaterialTheme.typography.bodyMedium.copy(color = TextSecondaryDark, fontWeight = FontWeight.Medium)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Row(verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = todayEscapesCount.toString(),
                        style = MaterialTheme.typography.displayLarge.copy(
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.Light,
                            color = PrimaryAccent,
                            fontSize = 64.sp
                        )
                    )
                    Text(
                        text = "ESCAPES",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = TextPrimaryDark,
                            letterSpacing = 1.sp
                        ),
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                }

                // Mini Histogram Bars - KEPT EXACTLY AS IT WAS ORIGINALLY
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.Bottom,
                    modifier = Modifier.height(40.dp)
                ) {
                    val barHeights = listOf(0.25f, 0.5f, 0.75f, 1.0f, 0.65f, 0.35f)
                    barHeights.forEach { fraction ->
                        Box(
                            modifier = Modifier
                                .width(12.dp)
                                .fillMaxHeight(fraction)
                                .clip(RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp))
                                .background(PrimaryAccent)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            HorizontalDivider(color = Color(0x26FFFFFF), thickness = 1.dp)
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "INDIVIDUAL APP CHARTS",
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = PrimaryAccent,
                    letterSpacing = 1.2.sp
                ),
                modifier = Modifier.padding(bottom = 10.dp)
            )

            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                AppDetailRow(name = "YouTube Shorts", packageName = AppConstants.PACKAGE_YOUTUBE, count = youtubeCount, color = YouTubeRed)
                AppDetailRow(name = "Instagram Reels", packageName = AppConstants.PACKAGE_INSTAGRAM, count = instagramCount, color = InstagramPurple)
                AppDetailRow(name = "Facebook Reels", packageName = AppConstants.PACKAGE_FACEBOOK, count = facebookCount, color = FacebookBlue)
            }
        }
    }
}

@Composable
fun AppDetailRow(name: String, packageName: String, count: Int, color: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0x12FFFFFF))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.weight(1f)
        ) {
            AppIcon(
                packageName = packageName,
                fallbackColor = color,
                iconSize = 36,
                innerFallbackSize = 14
            )
            Column {
                Text(text = name, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold, color = TextPrimaryDark))
                Text(text = "$count escapes today", style = MaterialTheme.typography.bodySmall.copy(color = TextSecondaryDark, fontSize = 11.sp))
            }
        }

        // Mini app-specific chart (7px wide bars, distinct shape based on app package)
        Row(
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier.height(32.dp)
        ) {
            val barHeights = getAppSpecificMockHeights(packageName, count)
            barHeights.forEach { fraction ->
                Box(
                    modifier = Modifier
                        .width(7.dp)
                        .fillMaxHeight(fraction)
                        .clip(RoundedCornerShape(topStart = 2.dp, topEnd = 2.dp))
                        .background(color)
                )
            }
        }
    }
}

@Composable
fun RecentEventItem(event: EscapeEventEntity) {
    val dateFormat = SimpleDateFormat("HH:mm - MMM dd", Locale.getDefault())
    val timeStr = dateFormat.format(Date(event.timestamp))

    Card(
        colors = CardDefaults.cardColors(containerColor = SurfaceDark),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, CardBorderDark, RoundedCornerShape(16.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "${event.appName} (${event.contentType})",
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold, color = TextPrimaryDark)
                )
                Text(
                    text = "Rule: ${event.detectionRule} • $timeStr",
                    style = MaterialTheme.typography.bodySmall.copy(color = TextSecondaryDark, fontSize = 11.sp)
                )
            }

            Text(
                text = "${event.confidenceScore}%",
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = PrimaryAccent
                )
            )
        }
    }
}
