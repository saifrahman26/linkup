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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.People
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.OrbitRepository
import com.example.ui.components.OrbitHeader
import com.example.ui.theme.*

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun OnboardingInterestsScreen(
    onContinue: () -> Unit,
    onSkip: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val domains by OrbitRepository.domains.collectAsState()
    val selectedCount = domains.count { it.isSelected }

    Scaffold(
        topBar = {
            Column {
                OrbitHeader(
                    title = "STEP 1 OF 3",
                    showBack = true,
                    onBack = onBack,
                    showOrganizerBadge = false
                )
                LinearProgressIndicator(
                    progress = { 0.33f },
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
                            text = "$selectedCount domains selected",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = OnSurface
                        )
                        Text(
                            text = "Recommended: 3–6 domains",
                            fontSize = 11.sp,
                            color = Secondary
                        )
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        TextButton(
                            onClick = onSkip,
                            modifier = Modifier.testTag("interests_skip_button")
                        ) {
                            Text("Skip", color = Secondary, fontSize = 13.sp)
                        }

                        Button(
                            onClick = onContinue,
                            colors = ButtonDefaults.buttonColors(containerColor = Primary),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.testTag("interests_continue_button")
                        ) {
                            Text("Continue (1/3)", fontSize = 13.sp, fontWeight = FontWeight.Bold)
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Live Density Callout
            item {
                Surface(
                    color = Color(0xFFE6F7ED),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.People,
                            contentDescription = null,
                            tint = Color(0xFF00A86B),
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = "High density in AI & B2B SaaS today (612 checked-in)",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF006948)
                        )
                    }
                }
            }

            // Headline
            item {
                Column {
                    Text(
                        text = "What are you\ninterested in?",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = OnSurface,
                        lineHeight = 34.sp
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Select fields you are building in or exploring. Orbit uses this to cluster recommendations.",
                        fontSize = 13.sp,
                        color = Secondary,
                        lineHeight = 18.sp
                    )
                }
            }

            // Category: Tech & Architecture
            item {
                Text(
                    text = "ENGINEERING & AI SYSTEMS",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Secondary,
                    letterSpacing = 0.5.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    domains.filter { it.category == "tech" }.forEach { domain ->
                        DomainChip(
                            name = domain.name,
                            isSelected = domain.isSelected,
                            onClick = { OrbitRepository.toggleDomain(domain.id) }
                        )
                    }
                }
            }

            // Category: Venture & Business
            item {
                Text(
                    text = "VENTURE & GO-TO-MARKET",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Secondary,
                    letterSpacing = 0.5.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    domains.filter { it.category == "venture" }.forEach { domain ->
                        DomainChip(
                            name = domain.name,
                            isSelected = domain.isSelected,
                            onClick = { OrbitRepository.toggleDomain(domain.id) }
                        )
                    }
                }
            }

            // Category: Design & Product
            item {
                Text(
                    text = "PRODUCT & DESIGN",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Secondary,
                    letterSpacing = 0.5.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    domains.filter { it.category == "design" }.forEach { domain ->
                        DomainChip(
                            name = domain.name,
                            isSelected = domain.isSelected,
                            onClick = { OrbitRepository.toggleDomain(domain.id) }
                        )
                    }
                }
            }

            // Discovery Tip
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Lightbulb,
                            contentDescription = null,
                            tint = Primary,
                            modifier = Modifier.size(20.dp)
                        )
                        Column {
                            Text(
                                text = "Precision Intent Routing",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = OnSurface
                            )
                            Text(
                                text = "Next, you will define what you are seeking and offering so we match complementary skills rather than identical roles.",
                                fontSize = 11.sp,
                                color = Secondary,
                                lineHeight = 15.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DomainChip(
    name: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        color = if (isSelected) InverseSurface else SurfaceContainerLowest,
        shape = RoundedCornerShape(20.dp),
        border = if (isSelected) null else androidx.compose.foundation.BorderStroke(1.dp, OutlineVariant),
        modifier = Modifier
            .clickable(onClick = onClick)
            .testTag("domain_chip_$name")
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 9.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            if (isSelected) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(14.dp)
                )
            }
            Text(
                text = name,
                fontSize = 13.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = if (isSelected) Color.White else OnSurface
            )
        }
    }
}
