package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.OrbitRepository
import com.example.ui.components.OrbitHeader
import com.example.ui.theme.*

@Composable
fun OnboardingIntentsScreen(
    onContinue: () -> Unit,
    onSkip: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val intents by OrbitRepository.intents.collectAsState()
    val selectedCount = intents.count { it.isSelected }

    Scaffold(
        topBar = {
            Column {
                OrbitHeader(
                    title = "STEP 2 OF 3",
                    showBack = true,
                    onBack = onBack,
                    showOrganizerBadge = false
                )
                LinearProgressIndicator(
                    progress = { 0.66f },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp),
                    color = Primary,
                    trackColor = SurfaceContainerHigh
                )
            }
        },
        bottomBar = {
            Surface(
                color = SurfaceContainerLowest,
                shadowElevation = 8.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = "$selectedCount intents chosen",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = OnSurface
                        )
                        Text(
                            text = "Target: 1–3 primary goals",
                            fontSize = 11.sp,
                            color = Secondary
                        )
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        TextButton(
                            onClick = onSkip,
                            modifier = Modifier.testTag("intents_skip_button")
                        ) {
                            Text("Skip", color = Secondary, fontSize = 13.sp)
                        }

                        Button(
                            onClick = onContinue,
                            colors = ButtonDefaults.buttonColors(containerColor = Primary),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.testTag("intents_continue_button")
                        ) {
                            Text("Continue (2/3)", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            }
        },
        containerColor = Surface
    ) { innerPadding ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp),
            contentPadding = PaddingValues(top = 16.dp, bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Retained context pill from step 1
            item {
                Surface(
                    color = SurfaceContainerLow,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = Primary,
                            modifier = Modifier.size(14.dp)
                        )
                        Text(
                            text = "Base Focus: AI & Models, B2B SaaS, Infra",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Primary
                        )
                    }
                }
            }

            // Headline
            item {
                Column {
                    Text(
                        text = "What are you looking for today?",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = OnSurface,
                        lineHeight = 32.sp
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Select up to 3 priorities for Tech Summit 2026. This drives who appears on your Discover feed.",
                        fontSize = 13.sp,
                        color = Secondary,
                        lineHeight = 18.sp
                    )
                }
            }

            // Predictive Signal Card
            item {
                Surface(
                    color = Color(0xFFF0F5FF),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(Primary),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Bolt,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        Column {
                            Text(
                                text = "Immediate Signal Match: 94% Fit",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Primary
                            )
                            Text(
                                text = "28 active attendees in the Lounge currently offer Cofounder & Customer partnerships.",
                                fontSize = 11.sp,
                                color = OnSurfaceVariant,
                                lineHeight = 15.sp
                            )
                        }
                    }
                }
            }

            // Intent Items
            items(intents.size) { index ->
                val intent = intents[index]
                IntentCard(
                    intent = intent,
                    onClick = { OrbitRepository.toggleIntent(intent.id) }
                )
            }
        }
    }
}

@Composable
private fun IntentCard(
    intent: com.example.data.IntentAnchor,
    onClick: () -> Unit
) {
    val icon = when (intent.iconName) {
        "Handshake" -> Icons.Default.Handshake
        "Groups" -> Icons.Default.Groups
        "TrendingUp" -> Icons.Default.TrendingUp
        "Psychology" -> Icons.Default.Psychology
        "PersonAdd" -> Icons.Default.PersonAdd
        "Hub" -> Icons.Default.Hub
        "Forum" -> Icons.Default.Forum
        else -> Icons.Default.Star
    }

    Surface(
        color = if (intent.isSelected) SurfaceContainerLowest else SurfaceContainerLowest,
        shape = RoundedCornerShape(12.dp),
        border = if (intent.isSelected) {
            androidx.compose.foundation.BorderStroke(2.dp, Primary)
        } else {
            androidx.compose.foundation.BorderStroke(1.dp, OutlineVariant)
        },
        shadowElevation = if (intent.isSelected) 2.dp else 0.dp,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .testTag("intent_card_${intent.id}")
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(if (intent.isSelected) PrimaryContainer else SurfaceContainerHigh),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = if (intent.isSelected) Color.White else Primary,
                    modifier = Modifier.size(22.dp)
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = intent.title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = OnSurface
                )
                Text(
                    text = intent.description,
                    fontSize = 12.sp,
                    color = Secondary,
                    lineHeight = 16.sp,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }

            Checkbox(
                checked = intent.isSelected,
                onCheckedChange = { onClick() },
                colors = CheckboxDefaults.colors(
                    checkedColor = Primary,
                    checkmarkColor = Color.White
                )
            )
        }
    }
}
