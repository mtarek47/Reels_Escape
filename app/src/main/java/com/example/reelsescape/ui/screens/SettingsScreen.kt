package com.example.reelsescape.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.content.Intent
import android.net.Uri
import androidx.compose.ui.platform.LocalContext
import com.example.reelsescape.constants.AppConstants
import com.example.ui.theme.*
import com.example.reelsescape.ui.viewmodel.MainViewModel
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.graphics.PathFillType

// Custom Vector Logo for GitHub
val GitHubIcon = ImageVector.Builder(
    name = "GitHub",
    defaultWidth = 24.dp,
    defaultHeight = 24.dp,
    viewportWidth = 24f,
    viewportHeight = 24f
).path(
    fill = androidx.compose.ui.graphics.SolidColor(Color.White),
    pathFillType = PathFillType.NonZero
) {
    moveTo(12f, 2f)
    curveTo(6.477f, 2f, 2f, 6.477f, 2f, 12f)
    curveTo(2f, 16.42f, 4.87f, 20.17f, 8.84f, 21.5f)
    curveTo(9.34f, 21.58f, 9.5f, 21.27f, 9.5f, 21.0f)
    curveTo(9.5f, 20.77f, 9.5f, 20.0f, 9.5f, 19.14f)
    curveTo(6.72f, 19.74f, 6.13f, 18.0f, 5.91f, 17.38f)
    curveTo(5.53f, 16.4f, 5.0f, 16.14f, 5.0f, 16.14f)
    curveTo(4.09f, 15.52f, 5.0f, 15.53f, 5.0f, 15.53f)
    curveTo(6.0f, 15.6f, 6.53f, 16.56f, 6.53f, 16.56f)
    curveTo(7.42f, 18.08f, 8.87f, 17.64f, 9.44f, 17.39f)
    curveTo(9.53f, 16.74f, 9.79f, 16.3f, 10.0f, 16.1f)
    curveTo(7.78f, 15.86f, 5.45f, 11.16f, 5.45f, 11.16f)
    curveTo(5.45f, 10.07f, 5.84f, 9.17f, 6.47f, 8.47f)
    curveTo(6.37f, 8.21f, 6.03f, 7.2f, 6.57f, 5.8f)
    curveTo(6.57f, 5.8f, 7.4f, 5.54f, 9.3f, 6.82f)
    curveTo(10.1f, 6.6f, 10.95f, 6.5f, 11.8f, 6.5f)
    curveTo(12.65f, 6.5f, 13.5f, 6.6f, 14.3f, 6.82f)
    curveTo(16.2f, 5.54f, 17.03f, 5.8f, 17.03f, 5.8f)
    curveTo(17.57f, 7.2f, 17.23f, 8.21f, 17.13f, 8.47f)
    curveTo(17.76f, 9.17f, 18.15f, 10.07f, 18.15f, 11.16f)
    curveTo(18.15f, 15.0f, 15.8f, 15.85f, 13.57f, 16.1f)
    curveTo(13.93f, 16.4f, 14.25f, 17.0f, 14.25f, 17.9f)
    curveTo(14.25f, 19.2f, 14.25f, 20.2f, 14.25f, 20.5f)
    curveTo(14.25f, 20.77f, 14.4f, 21.1f, 14.9f, 21.0f)
    curveTo(18.87f, 19.67f, 21.75f, 15.93f, 21.75f, 12f)
    curveTo(21.75f, 6.477f, 17.273f, 2f, 12f, 2f)
    close()
}.build()

// Custom Vector Logo for LinkedIn
val LinkedInIcon = ImageVector.Builder(
    name = "LinkedIn",
    defaultWidth = 24.dp,
    defaultHeight = 24.dp,
    viewportWidth = 24f,
    viewportHeight = 24f
).path(
    fill = androidx.compose.ui.graphics.SolidColor(Color.White),
    pathFillType = PathFillType.NonZero
) {
    moveTo(19f, 3f)
    horizontalLineTo(5f)
    curveTo(3.895f, 3f, 3f, 3.895f, 3f, 5f)
    verticalLineTo(19f)
    curveTo(3f, 20.105f, 3.895f, 21f, 5f, 21f)
    horizontalLineTo(19f)
    curveTo(20.105f, 21f, 21f, 20.105f, 21f, 19f)
    verticalLineTo(5f)
    curveTo(21f, 3.895f, 20.105f, 3f, 19f, 3f)
    close()
    moveTo(9.01f, 19f)
    horizontalLineTo(6.01f)
    verticalLineTo(10f)
    horizontalLineTo(9.01f)
    verticalLineTo(19f)
    close()
    moveTo(7.51f, 8.56f)
    curveTo(6.55f, 8.56f, 5.77f, 7.78f, 5.77f, 6.82f)
    curveTo(5.77f, 5.86f, 6.55f, 5.08f, 7.51f, 5.08f)
    curveTo(8.47f, 5.08f, 9.25f, 5.86f, 9.25f, 6.82f)
    curveTo(9.25f, 7.78f, 8.47f, 8.56f, 7.51f, 8.56f)
    close()
    moveTo(18.01f, 19f)
    horizontalLineTo(15.01f)
    verticalLineTo(14.25f)
    curveTo(15.01f, 13.12f, 14.99f, 11.67f, 13.44f, 11.67f)
    curveTo(11.87f, 11.67f, 11.63f, 12.9f, 11.63f, 14.17f)
    verticalLineTo(19f)
    horizontalLineTo(8.63f)
    verticalLineTo(10f)
    horizontalLineTo(11.51f)
    verticalLineTo(11.23f)
    horizontalLineTo(11.55f)
    curveTo(11.95f, 10.47f, 12.93f, 9.67f, 14.39f, 9.67f)
    curveTo(17.43f, 9.67f, 18.01f, 11.67f, 18.01f, 14.27f)
    verticalLineTo(19f)
    close()
}.build()

@Composable
fun SettingsScreen(viewModel: MainViewModel) {
    val settings by viewModel.settingsState.collectAsState()
    var showClearDialog by remember { mutableStateOf(false) }

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
                text = "SYSTEM PREFERENCES",
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.5.sp,
                    color = PrimaryAccent
                ),
                modifier = Modifier.padding(start = 4.dp, top = 8.dp)
            )
        }

        // Master Toggle Card
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = SurfaceDark),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, CardBorderDark, RoundedCornerShape(20.dp))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Master Protection",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = TextPrimaryDark)
                        )
                        Text(
                            text = "Global toggle for all detection rules",
                            style = MaterialTheme.typography.bodySmall.copy(color = TextSecondaryDark)
                        )
                    }

                    Switch(
                        checked = settings.isMasterProtectionEnabled,
                        onCheckedChange = { viewModel.setMasterProtection(it) },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = OnPrimaryAccent,
                            checkedTrackColor = PrimaryAccent,
                            uncheckedThumbColor = TextSecondaryDark,
                            uncheckedTrackColor = SurfaceVariantDark
                        ),
                        modifier = Modifier.testTag("master_protection_switch")
                    )
                }
            }
        }

        // Sensitivity Card
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = SurfaceDark),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, CardBorderDark, RoundedCornerShape(20.dp))
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "Detection Sensitivity",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = TextPrimaryDark)
                    )
                    Text(
                        text = "Controls required confidence score for Back escape trigger.",
                        style = MaterialTheme.typography.bodySmall.copy(color = TextSecondaryDark)
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        SensitivityChip(
                            label = "Low (85%)",
                            selected = AppConstants.SENSITIVITY_LOW.equals(settings.sensitivity, ignoreCase = true),
                            onClick = { viewModel.setSensitivity(AppConstants.SENSITIVITY_LOW) },
                            modifier = Modifier.weight(1f)
                        )
                        SensitivityChip(
                            label = "Medium (70%)",
                            selected = AppConstants.SENSITIVITY_MEDIUM.equals(settings.sensitivity, ignoreCase = true),
                            onClick = { viewModel.setSensitivity(AppConstants.SENSITIVITY_MEDIUM) },
                            modifier = Modifier.weight(1f)
                        )
                        SensitivityChip(
                            label = "High (55%)",
                            selected = AppConstants.SENSITIVITY_HIGH.equals(settings.sensitivity, ignoreCase = true),
                            onClick = { viewModel.setSensitivity(AppConstants.SENSITIVITY_HIGH) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }

        // Developer Debug Mode Toggle
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = SurfaceDark),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, CardBorderDark, RoundedCornerShape(20.dp))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Developer Debug Mode",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = TextPrimaryDark)
                        )
                        Text(
                            text = "Log confidence scores & matched rules to Logcat",
                            style = MaterialTheme.typography.bodySmall.copy(color = TextSecondaryDark)
                        )
                    }

                    Switch(
                        checked = settings.isDebugMode,
                        onCheckedChange = { viewModel.setDebugMode(it) },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = OnPrimaryAccent,
                            checkedTrackColor = PrimaryAccent,
                            uncheckedThumbColor = TextSecondaryDark,
                            uncheckedTrackColor = SurfaceVariantDark
                        ),
                        modifier = Modifier.testTag("debug_mode_switch")
                    )
                }
            }
        }

        // Data & Management Actions
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = SurfaceDark),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, CardBorderDark, RoundedCornerShape(20.dp))
            ) {
                Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    TextButton(
                        onClick = { showClearDialog = true },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("clear_history_button")
                    ) {
                        Text("Clear Escape History", color = MaterialTheme.colorScheme.error, fontWeight = FontWeight.Bold)
                    }

                    HorizontalDivider(color = CardBorderDark)

                    TextButton(
                        onClick = { viewModel.resetSettings() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("reset_settings_button")
                    ) {
                        Text("Reset All Settings", color = TextSecondaryDark)
                    }
                }
            }
        }

        // Developer Details Section
        item {
            Text(
                text = "DEVELOPER INFO",
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.5.sp,
                    color = PrimaryAccent
                ),
                modifier = Modifier.padding(start = 4.dp, top = 8.dp)
            )
        }

        item {
            val context = LocalContext.current
            Card(
                colors = CardDefaults.cardColors(containerColor = SurfaceDark),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, CardBorderDark, RoundedCornerShape(20.dp))
            ) {
                Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(
                        text = "Developed by Tarek Parvez",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = TextPrimaryDark)
                    )
                    Text(
                        text = "Escape the infinite scrolling loops of modern apps. Designed to help you live mindfully.",
                        style = MaterialTheme.typography.bodySmall.copy(color = TextSecondaryDark, lineHeight = 16.sp)
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        // GitHub Button with Custom Vector Icon
                        Button(
                            onClick = {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/mtarek47"))
                                context.startActivity(intent)
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = SurfaceVariantDark),
                            shape = RoundedCornerShape(12.dp),
                            contentPadding = PaddingValues(vertical = 10.dp, horizontal = 12.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(
                                    imageVector = GitHubIcon,
                                    contentDescription = "GitHub",
                                    tint = Color.White,
                                    modifier = Modifier.size(18.dp)
                                )
                                Text("GitHub", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold, color = Color.White))
                            }
                        }

                        // LinkedIn Button with Custom Vector Icon
                        Button(
                            onClick = {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/m-tarek-rahman-06068a349/"))
                                context.startActivity(intent)
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0077B5)), // LinkedIn Official Blue
                            shape = RoundedCornerShape(12.dp),
                            contentPadding = PaddingValues(vertical = 10.dp, horizontal = 12.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(
                                    imageVector = LinkedInIcon,
                                    contentDescription = "LinkedIn",
                                    tint = Color.White,
                                    modifier = Modifier.size(18.dp)
                                )
                                Text("LinkedIn", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold, color = Color.White))
                            }
                        }
                    }
                }
            }
        }

        // About & Privacy Statement
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Reels Escape V1.0.2",
                    style = MaterialTheme.typography.bodySmall.copy(color = TextSecondaryDark, fontWeight = FontWeight.Bold)
                )
                Text(
                    text = "100% Local • Privacy-First Utility",
                    style = MaterialTheme.typography.labelSmall.copy(color = SystemArmedGreen)
                )
            }
        }
    }

    if (showClearDialog) {
        AlertDialog(
            onDismissRequest = { showClearDialog = false },
            title = { Text("Clear History?") },
            text = { Text("This will permanently remove all escape logs and statistics from this device.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.clearHistory()
                        showClearDialog = false
                    }
                ) {
                    Text("Clear", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showClearDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun SensitivityChip(label: String, selected: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected) PrimaryAccent else SurfaceVariantDark,
            contentColor = if (selected) OnPrimaryAccent else TextPrimaryDark
        ),
        shape = RoundedCornerShape(12.dp),
        contentPadding = PaddingValues(vertical = 8.dp, horizontal = 4.dp),
        modifier = modifier
    ) {
        Text(text = label, style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, fontSize = 10.sp))
    }
}
