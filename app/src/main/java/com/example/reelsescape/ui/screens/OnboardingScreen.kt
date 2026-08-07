package com.example.reelsescape.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*
import com.example.reelsescape.ui.viewmodel.MainViewModel

@Composable
fun OnboardingScreen(
    viewModel: MainViewModel,
    onComplete: () -> Unit
) {
    var step by remember { mutableIntStateOf(1) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (step) {
                1 -> {
                    Text(
                        text = "Escape the Scroll",
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = PrimaryAccent
                        ),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Reels Escape helps you stay focused by automatically leaving short-form video feeds like Shorts and Reels.",
                        style = MaterialTheme.typography.bodyLarge.copy(color = TextSecondaryDark),
                        textAlign = TextAlign.Center
                    )
                }
                2 -> {
                    Text(
                        text = "Privacy-First Protection",
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = SystemArmedGreen
                        ),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "We use Android Accessibility Service locally on your device. Zero data is recorded, saved, or sent to any server.",
                        style = MaterialTheme.typography.bodyLarge.copy(color = TextSecondaryDark),
                        textAlign = TextAlign.Center
                    )
                }
                3 -> {
                    Text(
                        text = "Select Protected Apps",
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = PrimaryAccent
                        ),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Protection will be active for YouTube Shorts, Instagram Reels, and Facebook Reels. You can customize them anytime.",
                        style = MaterialTheme.typography.bodyLarge.copy(color = TextSecondaryDark),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        Button(
            onClick = {
                if (step < 3) {
                    step++
                } else {
                    viewModel.setOnboardingCompleted(true)
                    onComplete()
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryAccent, contentColor = OnPrimaryAccent),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .align(Alignment.BottomCenter)
                .testTag("onboarding_next_button")
        ) {
            Text(
                text = if (step < 3) "Get Started" else "Enable Protection",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
            )
        }
    }
}
