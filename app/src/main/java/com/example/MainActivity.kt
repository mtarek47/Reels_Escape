package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.reelsescape.ui.screens.*
import com.example.ui.theme.BackgroundDark
import com.example.ui.theme.PrimaryAccent
import com.example.ui.theme.ReelsEscapeTheme
import com.example.ui.theme.SystemArmedGreen
import com.example.reelsescape.ui.viewmodel.MainViewModel
import com.example.reelsescape.util.AccessibilityUtils

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()
    private val isAccessibilityEnabledState = mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ReelsEscapeTheme {
                val settings by viewModel.settingsState.collectAsState()
                val isAccessibilityEnabled by isAccessibilityEnabledState

                LaunchedEffect(Unit) {
                    isAccessibilityEnabledState.value = AccessibilityUtils.isAccessibilityServiceEnabled(this@MainActivity)
                }

                if (!settings.isOnboardingCompleted) {
                    OnboardingScreen(
                        viewModel = viewModel,
                        onComplete = { viewModel.refreshSettings() }
                    )
                } else {
                    MainContainer(
                        viewModel = viewModel,
                        isAccessibilityEnabled = isAccessibilityEnabled
                    )
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.refreshSettings()
        isAccessibilityEnabledState.value = AccessibilityUtils.isAccessibilityServiceEnabled(this)
    }
}

enum class NavItem(val title: String, val icon: ImageVector, val tag: String) {
    DASHBOARD("Home", Icons.Default.Home, "nav_dashboard"),
    APPS("Apps", Icons.Default.Apps, "nav_apps"),
    STATISTICS("Stats", Icons.Default.BarChart, "nav_statistics"),
    SETTINGS("Settings", Icons.Default.Settings, "nav_settings")
}

@Composable
fun MainContainer(
    viewModel: MainViewModel,
    isAccessibilityEnabled: Boolean
) {
    var selectedItem by remember { mutableStateOf(NavItem.DASHBOARD) }

    Scaffold(
        topBar = {
            HeaderSection(isArmed = isAccessibilityEnabled)
        },
        bottomBar = {
            NavigationBar(
                containerColor = BackgroundDark,
                modifier = Modifier
                    .windowInsetsPadding(WindowInsets.navigationBars)
                    .height(72.dp)
            ) {
                NavItem.values().forEach { item ->
                    NavigationBarItem(
                        selected = selectedItem == item,
                        onClick = { selectedItem = item },
                        icon = { Icon(item.icon, contentDescription = item.title) },
                        label = {
                            Text(
                                text = item.title.uppercase(),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 10.sp
                                )
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                            selectedTextColor = PrimaryAccent,
                            indicatorColor = PrimaryAccent,
                            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                        ),
                        modifier = Modifier.testTag(item.tag)
                    )
                }
            }
        },
        containerColor = BackgroundDark
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (selectedItem) {
                NavItem.DASHBOARD -> DashboardScreen(
                    viewModel = viewModel,
                    isAccessibilityEnabled = isAccessibilityEnabled,
                    onNavigateToApps = { selectedItem = NavItem.APPS }
                )
                NavItem.APPS -> AppsScreen(
                    viewModel = viewModel,
                    isAccessibilityEnabled = isAccessibilityEnabled
                )
                NavItem.STATISTICS -> StatisticsScreen(viewModel = viewModel)
                NavItem.SETTINGS -> SettingsScreen(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun HeaderSection(isArmed: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = "Reels Escape",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = (-0.5).sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(
                            color = if (isArmed) SystemArmedGreen else MaterialTheme.colorScheme.error,
                            shape = androidx.compose.foundation.shape.CircleShape
                        )
                )
                Text(
                    text = if (isArmed) "SYSTEM ARMED" else "SYSTEM INACTIVE",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.2.sp,
                        color = if (isArmed) SystemArmedGreen else MaterialTheme.colorScheme.error
                    )
                )
            }
        }
    }
}
