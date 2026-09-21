package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.data.OrbitRepository
import com.example.ui.theme.*

@Composable
fun OrganizerDashboardScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedDashboardTab by remember { mutableStateOf(0) } // 0: Overview & Funnel, 1: Analytics & Health
    val funnelStages = OrbitRepository.funnelStages
    val liveHotspots = OrbitRepository.liveHotspots
    val liveFeed = OrbitRepository.liveMatchFeed

    Scaffold(
        topBar = {
            Surface(
                color = SurfaceContainerLowest,
                shadowElevation = 1.dp
            ) {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            IconButton(onClick = onBack, modifier = Modifier.testTag("organizer_back_button")) {
                                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                            }
                            Column {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Text(
                                        text = "Tech Summit 2026",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = OnSurface
                                    )
                                    Surface(
                                        color = Color(0xFFE6F7ED),
                                        shape = RoundedCornerShape(4.dp)
                                    ) {
                                        Text(
                                            text = "LIVE RADAR",
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF006948),
                                            modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                                        )
                                    }
                                }
                                Text(
                                    text = "Event Ops & Matchmaking Control Room",
                                    fontSize = 11.sp,
                                    color = Secondary
                                )
                            }
                        }
                    }

                    TabRow(
                        selectedTabIndex = selectedDashboardTab,
                        containerColor = SurfaceContainerLowest,
                        contentColor = Primary
                    ) {
                        Tab(
                            selected = selectedDashboardTab == 0,
                            onClick = { selectedDashboardTab = 0 },
                            text = { Text("Overview & Funnel", fontSize = 13.sp, fontWeight = FontWeight.Bold) },
                            modifier = Modifier.testTag("dashboard_tab_overview")
                        )
                        Tab(
                            selected = selectedDashboardTab == 1,
                            onClick = { selectedDashboardTab = 1 },
                            text = { Text("Analytics & Health", fontSize = 13.sp, fontWeight = FontWeight.Bold) },
                            modifier = Modifier.testTag("dashboard_tab_analytics")
                        )
                    }
                }
            }
        },
        containerColor = Surface
    ) { innerPadding ->
        if (selectedDashboardTab == 0) {
            OverviewFunnelTab(
                funnelStages = funnelStages,
                liveHotspots = liveHotspots,
                liveFeed = liveFeed,
                modifier = modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            )
        } else {
            AnalyticsHealthTab(
                modifier = modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            )
        }
    }
}

@Composable
private fun OverviewFunnelTab(
    funnelStages: List<com.example.data.FunnelStage>,
    liveHotspots: List<com.example.data.Hotspot>,
    liveFeed: List<com.example.data.LiveMatchPair>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // High-level KPI Grid
        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    MetricKpiCard(
                        title = "CHECKED IN",
                        value = "1,420",
                        subtitle = "+18% vs day 1",
                        isPositive = true,
                        modifier = Modifier.weight(1f)
                    )
                    MetricKpiCard(
                        title = "ENGAGEMENT",
                        value = "78.4%",
                        subtitle = "1,114 active peers",
                        isPositive = true,
                        modifier = Modifier.weight(1f)
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    MetricKpiCard(
                        title = "INTROS SENT",
                        value = "3,842",
                        subtitle = "+34% velocity",
                        isPositive = true,
                        modifier = Modifier.weight(1f)
                    )
                    MetricKpiCard(
                        title = "MUTUAL INTROS",
                        value = "2,690",
                        subtitle = "70.0% accept rate",
                        isPositive = true,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // Live Networking Funnel
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Core Live Networking Funnel",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = OnSurface
                        )
                        Text(
                            text = "7 Stages",
                            fontSize = 11.sp,
                            color = Primary,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    funnelStages.forEach { stage ->
                        Column(modifier = Modifier.padding(vertical = 6.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Surface(
                                        color = SecondaryContainer,
                                        shape = RoundedCornerShape(4.dp)
                                    ) {
                                        Text(
                                            text = stage.step,
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Primary,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                    Text(
                                        text = stage.name,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = OnSurface
                                    )
                                }

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text(
                                        text = stage.count,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = OnSurface
                                    )
                                    Text(
                                        text = stage.retention,
                                        fontSize = 11.sp,
                                        color = Secondary
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(4.dp))
                            LinearProgressIndicator(
                                progress = { stage.progress },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(6.dp)
                                    .clip(RoundedCornerShape(3.dp)),
                                color = Primary,
                                trackColor = SurfaceContainerHigh
                            )
                        }
                    }
                }
            }
        }

        // Live Intent Supply-Demand Matrix
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Intent Matrix & Supply-Demand Balance",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = OnSurface
                    )
                    Text(
                        text = "Seeking vs. Offering ratio among checked-in attendees",
                        fontSize = 11.sp,
                        color = Secondary,
                        modifier = Modifier.padding(top = 2.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    IntentBalanceRow("Cofounder Fit", seeking = 420, offering = 280)
                    IntentBalanceRow("Enterprise Pilots", seeking = 580, offering = 410)
                    IntentBalanceRow("Seed / Series A", seeking = 390, offering = 210)
                    IntentBalanceRow("Senior AI Talent", seeking = 510, offering = 290)
                }
            }
        }

        // Live Venue Hotspots
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Live Venue Hotspots",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = OnSurface
                        )
                        Text(
                            text = "Moscone West",
                            fontSize = 11.sp,
                            color = Color(0xFF006948),
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    liveHotspots.forEach { spot ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = spot.name,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = OnSurface
                                )
                                Text(
                                    text = spot.subtitle,
                                    fontSize = 11.sp,
                                    color = Secondary
                                )
                            }
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Surface(
                                    color = Color(0xFFE6F7ED),
                                    shape = RoundedCornerShape(6.dp)
                                ) {
                                    Text(
                                        text = "${spot.activeLinks} active",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF006948),
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Live Match Feed Ticker
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF00A86B))
                        )
                        Text(
                            text = "LIVE MUTUAL INTRO FEED",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF006948),
                            letterSpacing = 0.5.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    liveFeed.forEach { item ->
                        Surface(
                            color = SurfaceContainerLow,
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.SwapCalls,
                                    contentDescription = null,
                                    tint = Primary,
                                    modifier = Modifier.size(20.dp)
                                )
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "${item.personA} (${item.roleA}) ↔ ${item.personB} (${item.roleB})",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = OnSurface
                                    )
                                    Text(
                                        text = "${item.topic} · ${item.location}",
                                        fontSize = 11.sp,
                                        color = Secondary
                                    )
                                }
                                Text(
                                    text = item.timeAgo,
                                    fontSize = 10.sp,
                                    color = Secondary
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun AnalyticsHealthTab(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Health KPI Row
        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    MetricKpiCard(
                        title = "RECIPROCAL RATE",
                        value = "84.2%",
                        subtitle = "Mutual intro interest",
                        isPositive = true,
                        modifier = Modifier.weight(1f)
                    )
                    MetricKpiCard(
                        title = "MEDIAN RESPONSE",
                        value = "8.5 min",
                        subtitle = "Time to first intro",
                        isPositive = true,
                        modifier = Modifier.weight(1f)
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    MetricKpiCard(
                        title = "INTRO ACCEPTANCE",
                        value = "72.0%",
                        subtitle = "Positive connection rate",
                        isPositive = true,
                        modifier = Modifier.weight(1f)
                    )
                    MetricKpiCard(
                        title = "LINKEDIN PASSES",
                        value = "1,940",
                        subtitle = "Profiles exchanged",
                        isPositive = true,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // Connection Velocity Timeline
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Connection Velocity Over Time",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = OnSurface
                    )
                    Text(
                        text = "Intros initiated per hour across Moscone West",
                        fontSize = 11.sp,
                        color = Secondary,
                        modifier = Modifier.padding(top = 2.dp)
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    TimelineEventRow(
                        time = "10:30 AM",
                        label = "Morning Keynote Break",
                        rate = "384 intros/hr",
                        fillFraction = 0.60f
                    )
                    TimelineEventRow(
                        time = "1:00 PM",
                        label = "Lunch & Lounge Peak",
                        rate = "512 intros/hr",
                        fillFraction = 0.80f
                    )
                    TimelineEventRow(
                        time = "4:30 PM",
                        label = "Orbit Afternoon Mixer",
                        rate = "648 intros/hr",
                        fillFraction = 1.0f
                    )
                }
            }
        }

        // Top Domain Clusters
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Top Domain Clusters",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = OnSurface
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    DomainShareRow("AI & Foundation Models", 42)
                    DomainShareRow("B2B SaaS & Developer Tools", 28)
                    DomainShareRow("Cloud & GPU Infrastructure", 18)
                    DomainShareRow("Cybersecurity & Identity", 12)
                }
            }
        }

        // Match Quality Feedback
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Match Quality & Signal",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = OnSurface
                        )
                        Surface(
                            color = Color(0xFFE6F7ED),
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = "94% Positive",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF006948),
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Verified Concrete Outcomes Reported:",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Secondary
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    OutcomeBullet("48 follow-up technical architecture calls scheduled")
                    OutcomeBullet("12 enterprise pilot discussions initiated on-site")
                    OutcomeBullet("7 seed & angel term sheet reviews in progress")
                }
            }
        }
    }
}

@Composable
private fun MetricKpiCard(
    title: String,
    value: String,
    subtitle: String,
    isPositive: Boolean,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = modifier
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(
                text = title,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = Secondary,
                letterSpacing = 0.5.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold,
                color = OnSurface
            )
            Text(
                text = subtitle,
                fontSize = 11.sp,
                color = if (isPositive) Color(0xFF006948) else Secondary,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun IntentBalanceRow(name: String, seeking: Int, offering: Int) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(name, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = OnSurface)
            Text(
                text = "Seeking: $seeking | Offering: $offering",
                fontSize = 11.sp,
                color = Secondary
            )
        }
        Spacer(modifier = Modifier.height(3.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(3.dp))
        ) {
            val total = seeking + offering
            Box(
                modifier = Modifier
                    .weight(seeking.toFloat() / total)
                    .fillMaxHeight()
                    .background(Primary)
            )
            Box(
                modifier = Modifier
                    .weight(offering.toFloat() / total)
                    .fillMaxHeight()
                    .background(Color(0xFF00A86B))
            )
        }
    }
}

@Composable
private fun TimelineEventRow(
    time: String,
    label: String,
    rate: String,
    fillFraction: Float
) {
    Column(modifier = Modifier.padding(vertical = 6.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(label, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = OnSurface)
                Text(time, fontSize = 11.sp, color = Secondary)
            }
            Text(rate, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Primary)
        }
        Spacer(modifier = Modifier.height(4.dp))
        LinearProgressIndicator(
            progress = { fillFraction },
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(3.dp)),
            color = Primary,
            trackColor = SurfaceContainerHigh
        )
    }
}

@Composable
private fun DomainShareRow(domain: String, percentage: Int) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(domain, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = OnSurface)
            Text("$percentage%", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Primary)
        }
        Spacer(modifier = Modifier.height(3.dp))
        LinearProgressIndicator(
            progress = { percentage / 100f },
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(3.dp)),
            color = Primary,
            trackColor = SurfaceContainerHigh
        )
    }
}

@Composable
private fun OutcomeBullet(text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(vertical = 2.dp)
    ) {
        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = null,
            tint = Color(0xFF00A86B),
            modifier = Modifier.size(16.dp)
        )
        Text(text = text, fontSize = 12.sp, color = OnSurfaceVariant)
    }
}
