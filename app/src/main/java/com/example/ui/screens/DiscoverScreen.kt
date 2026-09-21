package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.BookmarkBorder
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.data.Attendee
import com.example.data.ConnectionStatus
import com.example.data.OrbitRepository
import com.example.ui.components.MatchScoreBadge
import com.example.ui.components.StatusPill
import com.example.ui.theme.*

@Composable
fun DiscoverScreen(
    onAttendeeClick: (Attendee) -> Unit,
    onConnectClick: (Attendee) -> Unit,
    onOrganizerClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val attendees by OrbitRepository.attendees.collectAsState()
    var selectedFilter by remember { mutableStateOf("All") }
    val filters = listOf("All", "Cofounders", "AI/Systems", "Angel/Seed")

    val filteredAttendees = remember(attendees, selectedFilter) {
        if (selectedFilter == "All") attendees
        else attendees.filter { it.category == selectedFilter }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Active Radar Header
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
                                text = "DAY 2 · ACTIVE RADAR",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF006948),
                                letterSpacing = 0.5.sp
                            )
                        }

                        Surface(
                            color = SurfaceContainerHigh,
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = "Lounge Level 2",
                                fontSize = 11.sp,
                                color = Secondary,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "People you should meet.",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = OnSurface
                    )
                    Text(
                        text = "Curated based on your intent for Cofounders, Customers, and Distributed Systems.",
                        fontSize = 12.sp,
                        color = Secondary,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
            }
        }

        // Category Filter Chips
        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(filters) { filter ->
                    val isSelected = selectedFilter == filter
                    FilterChip(
                        selected = isSelected,
                        onClick = { selectedFilter = filter },
                        label = {
                            Text(
                                text = filter,
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Primary,
                            selectedLabelColor = Color.White,
                            containerColor = SurfaceContainerLowest,
                            labelColor = OnSurface
                        ),
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier.testTag("filter_chip_$filter")
                    )
                }
            }
        }

        // Discover Match Cards
        items(filteredAttendees, key = { it.id }) { attendee ->
            MatchCard(
                attendee = attendee,
                onClick = { onAttendeeClick(attendee) },
                onConnect = { onConnectClick(attendee) },
                onToggleBookmark = { OrbitRepository.toggleSaveAttendee(attendee.id) }
            )
        }
    }
}

@Composable
fun MatchCard(
    attendee: Attendee,
    onClick: () -> Unit,
    onConnect: () -> Unit,
    onToggleBookmark: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .testTag("match_card_${attendee.id}")
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header Row: Avatar, Info, Bookmark
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(54.dp)
                        .clip(CircleShape)
                        .border(2.dp, PrimaryFixed, CircleShape)
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(attendee.avatarUrl)
                            .crossfade(true)
                            .build(),
                        contentDescription = attendee.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = attendee.name,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = OnSurface
                        )
                        if (attendee.verified) {
                            Icon(
                                imageVector = Icons.Default.Verified,
                                contentDescription = "Verified Delegate",
                                tint = Primary,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }

                    Text(
                        text = "${attendee.title} · ${attendee.company}",
                        fontSize = 12.sp,
                        color = Secondary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(modifier = Modifier.height(4.dp))
                    StatusPill(text = attendee.status, isLive = attendee.isAvailable)
                }

                IconButton(
                    onClick = onToggleBookmark,
                    modifier = Modifier.size(36.dp).testTag("bookmark_${attendee.id}")
                ) {
                    Icon(
                        imageVector = if (attendee.isSaved) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                        contentDescription = "Bookmark",
                        tint = if (attendee.isSaved) Primary else Outline
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Match Score & Badge Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                MatchScoreBadge(score = attendee.matchScore)

                Surface(
                    color = SecondaryContainer,
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = attendee.badge,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = Primary,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Building description
            Text(
                text = attendee.building,
                fontSize = 13.sp,
                color = OnSurfaceVariant,
                lineHeight = 18.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Why meet AI Rationale Callout
            Surface(
                color = Color(0xFFF0F5FF),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(10.dp),
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = null,
                        tint = Primary,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = attendee.whyMeetReason,
                        fontSize = 11.sp,
                        lineHeight = 15.sp,
                        color = Primary,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Bottom Actions: View Profile & Connect
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedButton(
                    onClick = onClick,
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = OnSurface),
                    modifier = Modifier
                        .weight(1f)
                        .height(42.dp)
                        .testTag("view_profile_${attendee.id}")
                ) {
                    Text("View Profile", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                }

                when (attendee.connectionStatus) {
                    ConnectionStatus.CONNECTED -> {
                        FilledTonalButton(
                            onClick = onClick,
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.filledTonalButtonColors(
                                containerColor = Color(0xFFE6F7ED),
                                contentColor = Color(0xFF006948)
                            ),
                            modifier = Modifier
                                .weight(1f)
                                .height(42.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Connected", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                    ConnectionStatus.REQUESTED -> {
                        FilledTonalButton(
                            onClick = {},
                            enabled = false,
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .weight(1f)
                                .height(42.dp)
                        ) {
                            Text("Intro Sent", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                    else -> {
                        Button(
                            onClick = onConnect,
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Primary),
                            modifier = Modifier
                                .weight(1f)
                                .height(42.dp)
                                .testTag("connect_button_${attendee.id}")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Bolt,
                                contentDescription = null,
                                modifier = Modifier.size(14.dp),
                                tint = Color.White
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Connect ⚡", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        }
                    }
                }
            }
        }
    }
}
