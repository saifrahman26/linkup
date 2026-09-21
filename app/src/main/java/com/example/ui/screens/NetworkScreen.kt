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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.data.Attendee
import com.example.data.ConnectionStatus
import com.example.data.OrbitRepository
import com.example.data.PendingIntro
import com.example.ui.components.StatusPill
import com.example.ui.theme.*

@Composable
fun NetworkScreen(
    onAttendeeClick: (Attendee) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedSection by remember { mutableStateOf("requests") } // "requests", "connected", "saved"
    val pendingIntros by OrbitRepository.pendingIntros.collectAsState()
    val attendees by OrbitRepository.attendees.collectAsState()

    val connectedAttendees = remember(attendees) {
        attendees.filter { it.connectionStatus == ConnectionStatus.CONNECTED }
    }

    val savedAttendees = remember(attendees) {
        attendees.filter { it.isSaved }
    }

    var showBadgeDialog by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Digital Pass Banner
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = Primary),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showBadgeDialog = true }
                    .testTag("digital_pass_banner")
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.QrCode,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = "DIGITAL EVENT PASS",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = PrimaryFixed,
                                letterSpacing = 0.5.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Tap to Show Instant QR Badge",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "Elena Vance · Vesper AI · Delegate #8491",
                            fontSize = 12.sp,
                            color = PrimaryFixed
                        )
                    }

                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.QrCode2,
                            contentDescription = "QR Code",
                            tint = Primary,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
            }
        }

        // Segmented Control Tabs
        item {
            TabRow(
                selectedTabIndex = when (selectedSection) {
                    "requests" -> 0
                    "connected" -> 1
                    else -> 2
                },
                containerColor = SurfaceContainerLowest,
                contentColor = Primary,
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .fillMaxWidth()
            ) {
                Tab(
                    selected = selectedSection == "requests",
                    onClick = { selectedSection = "requests" },
                    text = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text("Requests", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                            if (pendingIntros.isNotEmpty()) {
                                Surface(
                                    color = Primary,
                                    shape = CircleShape
                                ) {
                                    Text(
                                        text = pendingIntros.size.toString(),
                                        fontSize = 10.sp,
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 1.dp)
                                    )
                                }
                            }
                        }
                    },
                    modifier = Modifier.testTag("tab_requests")
                )

                Tab(
                    selected = selectedSection == "connected",
                    onClick = { selectedSection = "connected" },
                    text = {
                        Text(
                            "Connected (${connectedAttendees.size})",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    modifier = Modifier.testTag("tab_connected")
                )

                Tab(
                    selected = selectedSection == "saved",
                    onClick = { selectedSection = "saved" },
                    text = {
                        Text(
                            "Saved (${savedAttendees.size})",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    modifier = Modifier.testTag("tab_saved")
                )
            }
        }

        // Section Content
        when (selectedSection) {
            "requests" -> {
                if (pendingIntros.isEmpty()) {
                    item {
                        EmptyStateCard(
                            title = "No Pending Intro Requests",
                            description = "You're all caught up! Browse the Discover feed to initiate new high-signal intros."
                        )
                    }
                } else {
                    items(pendingIntros, key = { it.id }) { intro ->
                        PendingIntroCard(
                            intro = intro,
                            onAccept = { OrbitRepository.acceptIntro(intro.id) },
                            onDecline = { OrbitRepository.declineIntro(intro.id) },
                            onClick = { onAttendeeClick(intro.attendee) }
                        )
                    }
                }
            }

            "connected" -> {
                if (connectedAttendees.isEmpty()) {
                    item {
                        EmptyStateCard(
                            title = "No Connections Yet",
                            description = "Accept incoming requests or connect with suggested peers to start networking."
                        )
                    }
                } else {
                    items(connectedAttendees, key = { it.id }) { attendee ->
                        ConnectedAttendeeCard(
                            attendee = attendee,
                            onClick = { onAttendeeClick(attendee) }
                        )
                    }
                }
            }

            "saved" -> {
                if (savedAttendees.isEmpty()) {
                    item {
                        EmptyStateCard(
                            title = "No Saved Attendees",
                            description = "Bookmark profiles on the Discover radar to easily follow up with them later."
                        )
                    }
                } else {
                    items(savedAttendees, key = { it.id }) { attendee ->
                        ConnectedAttendeeCard(
                            attendee = attendee,
                            onClick = { onAttendeeClick(attendee) }
                        )
                    }
                }
            }
        }

        // Lounge Pro Tip
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Coffee,
                        contentDescription = null,
                        tint = Primary,
                        modifier = Modifier.size(22.dp)
                    )
                    Column {
                        Text(
                            text = "Meet at Orbit Lounge Level 2",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = OnSurface
                        )
                        Text(
                            text = "Dedicated tables, high-speed WiFi, and complimentary espresso available for Orbit members.",
                            fontSize = 11.sp,
                            color = Secondary,
                            lineHeight = 15.sp
                        )
                    }
                }
            }
        }
    }

    // QR Code Badge Dialog
    if (showBadgeDialog) {
        AlertDialog(
            onDismissRequest = { showBadgeDialog = false },
            title = {
                Text(
                    text = "Orbit In-Person Pass",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(160.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.White)
                            .border(1.dp, OutlineVariant, RoundedCornerShape(12.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.QrCode2,
                            contentDescription = "QR Code",
                            tint = Color(0xFF151D21),
                            modifier = Modifier.size(130.dp)
                        )
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Elena Vance",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = OnSurface
                        )
                        Text(
                            text = "Founder & CEO · Vesper AI",
                            fontSize = 12.sp,
                            color = Secondary
                        )
                        Text(
                            text = "Tech Summit 2026 Delegate #8491",
                            fontSize = 11.sp,
                            color = Primary,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }

                    Text(
                        text = "Have another attendee scan this code to instantly exchange contacts and add each other to your Orbit network.",
                        fontSize = 11.sp,
                        color = Secondary,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        lineHeight = 15.sp
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = { showBadgeDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = Primary)
                ) {
                    Text("Close")
                }
            }
        )
    }
}

@Composable
private fun PendingIntroCard(
    intro: PendingIntro,
    onAccept: () -> Unit,
    onDecline: () -> Unit,
    onClick: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .testTag("intro_card_${intro.id}")
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .clip(CircleShape)
                        .border(2.dp, PrimaryFixed, CircleShape)
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(intro.attendee.avatarUrl)
                            .crossfade(true)
                            .build(),
                        contentDescription = intro.attendee.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = intro.attendee.name,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = OnSurface
                        )
                        Text(
                            text = intro.requestedTimeAgo,
                            fontSize = 11.sp,
                            color = Secondary
                        )
                    }

                    Text(
                        text = "${intro.attendee.title} · ${intro.attendee.company}",
                        fontSize = 12.sp,
                        color = Secondary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(modifier = Modifier.height(4.dp))
                    StatusPill(text = intro.attendee.status, isLive = true)
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Mutual Tag / Offer Tag
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Surface(
                    color = SecondaryContainer,
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = intro.mutualTag,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = Primary,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }

                if (intro.offerTag != null) {
                    Surface(
                        color = Color(0xFFE6F7ED),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = intro.offerTag,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF006948),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }
                }
            }

            if (intro.personalNote != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Surface(
                    color = SurfaceContainerLow,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "\"${intro.personalNote}\"",
                        fontSize = 12.sp,
                        color = OnSurfaceVariant,
                        lineHeight = 16.sp,
                        modifier = Modifier.padding(10.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedButton(
                    onClick = onDecline,
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Secondary),
                    modifier = Modifier
                        .weight(1f)
                        .height(42.dp)
                        .testTag("decline_intro_${intro.id}")
                ) {
                    Text("Decline", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                }

                Button(
                    onClick = onAccept,
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Primary),
                    modifier = Modifier
                        .weight(1f)
                        .height(42.dp)
                        .testTag("accept_intro_${intro.id}")
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Accept Intro", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }
            }
        }
    }
}

@Composable
private fun ConnectedAttendeeCard(
    attendee: Attendee,
    onClick: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .border(1.5.dp, PrimaryFixed, CircleShape)
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
                Text(
                    text = attendee.name,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = OnSurface
                )
                Text(
                    text = "${attendee.title} · ${attendee.company}",
                    fontSize = 12.sp,
                    color = Secondary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = attendee.mutualContext,
                    fontSize = 11.sp,
                    color = Primary,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }

            FilledTonalButton(
                onClick = onClick,
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                colors = ButtonDefaults.filledTonalButtonColors(
                    containerColor = SurfaceContainerHigh,
                    contentColor = Primary
                ),
                modifier = Modifier.height(36.dp)
            ) {
                Text("Profile", fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun EmptyStateCard(title: String, description: String) {
    Card(
        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
        shape = RoundedCornerShape(14.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Inbox,
                contentDescription = null,
                tint = Secondary,
                modifier = Modifier.size(36.dp)
            )
            Text(
                text = title,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = OnSurface
            )
            Text(
                text = description,
                fontSize = 12.sp,
                color = Secondary,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                lineHeight = 16.sp
            )
        }
    }
}
