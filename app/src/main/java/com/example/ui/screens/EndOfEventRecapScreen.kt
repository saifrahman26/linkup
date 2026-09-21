package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import com.example.data.Attendee
import com.example.data.OrbitRepository
import com.example.ui.components.OrbitHeader
import com.example.ui.theme.*

@Composable
fun EndOfEventRecapScreen(
    onAttendeeClick: (Attendee) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val attendees by OrbitRepository.attendees.collectAsState()
    val meetings by OrbitRepository.meetings.collectAsState()

    val connectedList = attendees.filter { it.connectionStatus == com.example.data.ConnectionStatus.CONNECTED }
    val recommendedList = attendees.take(5)

    Scaffold(
        topBar = {
            OrbitHeader(
                title = "EVENT RECAP",
                showBack = true,
                onBack = onBack,
                showOrganizerBadge = false
            )
        },
        containerColor = Surface
    ) { innerPadding ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(top = 16.dp, bottom = 36.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Hero Title Card
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Primary),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Surface(
                            color = PrimaryContainer,
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = "TECH SUMMIT '26 COMPLETE",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }
                        Text(
                            text = "Your Summit Networking Recap",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White
                        )
                        Text(
                            text = "Here's a breakdown of your 1-on-1 meetings, new mutual connections, and follow-up items.",
                            fontSize = 13.sp,
                            color = PrimaryFixed,
                            lineHeight = 18.sp
                        )
                    }
                }
            }

            // 4 Summary Metric Tiles: Recommended, Meetings, Connections, Follow-ups
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    RecapMetricTile(
                        modifier = Modifier.weight(1f),
                        value = "5",
                        label = "Recommended",
                        subtext = "High signal fit"
                    )
                    RecapMetricTile(
                        modifier = Modifier.weight(1f),
                        value = "${meetings.size}",
                        label = "Meetings",
                        subtext = "Requested & held"
                    )
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    RecapMetricTile(
                        modifier = Modifier.weight(1f),
                        value = "${connectedList.size + 2}",
                        label = "Connections",
                        subtext = "Passes exchanged"
                    )
                    RecapMetricTile(
                        modifier = Modifier.weight(1f),
                        value = "3",
                        label = "Follow-ups",
                        subtext = "Action items"
                    )
                }
            }

            // Section: Follow-up Actions
            item {
                Text(
                    text = "NEXT STEPS & FOLLOW-UPS",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Secondary,
                    letterSpacing = 0.5.sp
                )
            }

            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        FollowUpItem(
                            title = "Send Agent Benchmark deck to Marcus Chen",
                            subtext = "Marcus requested seed valuation metrics & evaluation harness."
                        )
                        HorizontalDivider(color = OutlineVariant)
                        FollowUpItem(
                            title = "Setup scaling sync with Aisha Khan",
                            subtext = "Compare GPU cluster latency against Vesper inference kernel."
                        )
                        HorizontalDivider(color = OutlineVariant)
                        FollowUpItem(
                            title = "Review Triton kernel drafts from Sarah Lin",
                            subtext = "Offered custom attention kernel benchmarking."
                        )
                    }
                }
            }

            // Section: Your Met People
            item {
                Text(
                    text = "PEOPLE MET & CONNECTED",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Secondary,
                    letterSpacing = 0.5.sp
                )
            }

            items(recommendedList, key = { it.id }) { person ->
                Card(
                    colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onAttendeeClick(person) }
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(42.dp)
                                .clip(CircleShape)
                        ) {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(person.avatarUrl)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = person.name,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        }

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = person.name,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = OnSurface
                            )
                            Text(
                                text = "${person.title} · ${person.company}",
                                fontSize = 12.sp,
                                color = Secondary
                            )
                        }

                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = Color(0xFF00A86B),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            // Export & Share
            item {
                Button(
                    onClick = onBack,
                    colors = ButtonDefaults.buttonColors(containerColor = Primary),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("recap_done_button")
                ) {
                    Text("Return to Networking Plan", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
private fun RecapMetricTile(
    value: String,
    label: String,
    subtext: String,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                text = value,
                fontSize = 26.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Primary
            )
            Text(
                text = label,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = OnSurface
            )
            Text(
                text = subtext,
                fontSize = 11.sp,
                color = Secondary
            )
        }
    }
}

@Composable
private fun FollowUpItem(title: String, subtext: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Icon(
            imageVector = Icons.Default.TaskAlt,
            contentDescription = null,
            tint = Primary,
            modifier = Modifier
                .size(18.dp)
                .padding(top = 2.dp)
        )
        Column {
            Text(
                text = title,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = OnSurface
            )
            Text(
                text = subtext,
                fontSize = 11.sp,
                color = Secondary,
                lineHeight = 15.sp
            )
        }
    }
}
