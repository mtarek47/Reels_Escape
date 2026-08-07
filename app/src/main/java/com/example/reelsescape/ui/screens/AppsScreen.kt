package com.example.reelsescape.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.reelsescape.constants.AppConstants
import com.example.reelsescape.ui.components.AppIcon
import com.example.ui.theme.*
import com.example.reelsescape.ui.viewmodel.MainViewModel

@Composable
fun AppsScreen(
    viewModel: MainViewModel,
    isAccessibilityEnabled: Boolean
) {
    val settings by viewModel.settingsState.collectAsState()

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
                text = "MANAGED APPLICATIONS",
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.5.sp,
                    color = PrimaryAccent
                ),
                modifier = Modifier.padding(start = 4.dp, top = 8.dp)
            )
        }

        item {
            AppToggleCard(
                appName = "YouTube",
                packageName = AppConstants.PACKAGE_YOUTUBE,
                protectionType = "Shorts Auto-Escape",
                color = YouTubeRed,
                isEnabled = settings.isYoutubeEnabled,
                onToggle = { viewModel.setYoutubeEnabled(it) },
                testTag = "youtube_switch"
            )
        }

        item {
            AppToggleCard(
                appName = "Instagram",
                packageName = AppConstants.PACKAGE_INSTAGRAM,
                protectionType = "Reels Auto-Escape",
                color = InstagramPurple,
                isEnabled = settings.isInstagramEnabled,
                onToggle = { viewModel.setInstagramEnabled(it) },
                testTag = "instagram_switch"
            )
        }

        item {
            AppToggleCard(
                appName = "Facebook",
                packageName = AppConstants.PACKAGE_FACEBOOK,
                protectionType = "Reels Auto-Escape",
                color = FacebookBlue,
                isEnabled = settings.isFacebookEnabled,
                onToggle = { viewModel.setFacebookEnabled(it) },
                testTag = "facebook_switch"
            )
        }

        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = SurfaceDark),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, CardBorderDark, RoundedCornerShape(20.dp))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "How Protection Works",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = TextPrimaryDark)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "When an enabled app opens its short-form video tab, Reels Escape inspects the screen hierarchy locally and triggers a Back navigation before the video feed distracts you.",
                        style = MaterialTheme.typography.bodySmall.copy(color = TextSecondaryDark, lineHeight = 18.sp)
                    )
                }
            }
        }
    }
}

@Composable
fun AppToggleCard(
    appName: String,
    packageName: String,
    protectionType: String,
    color: Color,
    isEnabled: Boolean,
    onToggle: (Boolean) -> Unit,
    testTag: String
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = SurfaceDark),
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, CardBorderDark, RoundedCornerShape(24.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                AppIcon(
                    packageName = packageName,
                    fallbackColor = color,
                    iconSize = 44,
                    innerFallbackSize = 18
                )

                Column {
                    Text(
                        text = appName,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = TextPrimaryDark
                        )
                    )
                    Text(
                        text = protectionType,
                        style = MaterialTheme.typography.bodySmall.copy(color = TextSecondaryDark)
                    )
                }
            }

            Switch(
                checked = isEnabled,
                onCheckedChange = onToggle,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = OnPrimaryAccent,
                    checkedTrackColor = PrimaryAccent,
                    uncheckedThumbColor = TextSecondaryDark,
                    uncheckedTrackColor = SurfaceVariantDark
                ),
                modifier = Modifier.testTag(testTag)
            )
        }
    }
}
