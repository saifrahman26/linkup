package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.data.Attendee
import com.example.data.ConnectionStatus
import com.example.data.MeetingStatus
import com.example.data.OrbitRepository
import com.example.ui.components.StatusPill
import com.example.ui.theme.*

@Composable
fun NetworkingPlanScreen(
    onAttendeeClick: (Attendee) -> Unit,
    onRequestMeetingClick: (Attendee) -> Unit,
    onViewScheduleClick: () -> Unit,
    onOrganizerClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val attendees by OrbitRepository.attendees.collectAsState()
    val meetings by OrbitRepository.meetings.collectAsState()

    // 5 curated people worth meeting today
    val curatedFive = remember(attendees) {
        attendees.take(5)
    }

    // Check if there is an active meeting in progress or starting soon
    val nextMeeting = meetings.firstOrNull { it.status == MeetingStatus.ACCEPTED_SCHEDULED }
    val incomingRequest = meetings.firstOrNull { it.status == MeetingStatus.INCOMING_REQUEST }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 28.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Active Meeting Countdown Header if one exists
        if (nextMeeting != null) {
            item {
                ActiveMeetingCountdownCard(
                    meeting = nextMeeting,
                    onOpenProfile = { onAttendeeClick(nextMeeting.attendee) },
                    onImHere = { OrbitRepository.markArrivedAtMeeting(nextMeeting.id) }
                )
            }
        }

        // Incoming Request Banner if pending
        if (incomingRequest != null) {
            item {
                IncomingRequestAlertCard(
                    meeting = incomingRequest,
                    onAccept = { OrbitRepository.acceptMeeting(incomingRequest.id) },
                    onDecline = { OrbitRepository.declineMeeting(incomingRequest.id) },
                    onOpenProfile = { onAttendeeClick(incomingRequest.attendee) }
                )
            }
        }

        // Main Title Header
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
                                text = "TECH SUMMIT '26 · LIVE CONCIERGE",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF006948),
                                letterSpacing = 0.5.sp
                            )
                        }

                        Surface(
                            color = PrimaryFixed,
                            shape = RoundedCornerShape(6.dp),
                            modifier = Modifier.clickable { onViewScheduleClick() }
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CalendarToday,
                                    contentDescription = null,
                                    tint = Primary,
                                    modifier = Modifier.size(12.dp)
                                )
                                Text(
                                    text = "Your Day",
                                    fontSize = 11.sp,
                                    color = Primary,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Your Networking Plan",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = OnSurface
                    )
                    Text(
                        text = "5 people worth meeting today",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Primary,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                    Text(
                        text = "High-signal matches grounded in what you're building and what you need right now.",
                        fontSize = 12.sp,
                        color = Secondary,
                        lineHeight = 16.sp,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }

        // The 5 Curated People Cards
        itemsIndexed(curatedFive, key = { _, it -> it.id }) { index, person ->
            NetworkingPlanCard(
                index = index + 1,
                attendee = person,
                onClick = { onAttendeeClick(person) },
                onRequestMeeting = { onRequestMeetingClick(person) }
            )
        }

        // Footer helper
        item {
            Surface(
                color = SurfaceContainerLow,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = Primary,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "Orbit continuously refreshes your plan as sessions end and new attendees check into the Lounge.",
                        fontSize = 11.sp,
                        color = Secondary,
                        lineHeight = 15.sp
                    )
                }
            }
        }
    }
}

@Composable
fun NetworkingPlanCard(
    index: Int,
    attendee: Attendee,
    onClick: () -> Unit,
    onRequestMeeting: () -> Unit,
    modifier: Modifier = Modifier
) {
    val formattedIndex = String.format("%02d", index)

    Card(
        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .testTag("networking_plan_card_${attendee.id}")
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Header row: Number + Avatar + Name + Title
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Number Index Pill
                Surface(
                    color = PrimaryFixed,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = formattedIndex,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Primary,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }

                // Avatar
                Box(
                    modifier = Modifier
                        .size(46.dp)
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

                // Name & Subtitle
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = attendee.name,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = OnSurface
                    )
                    Text(
                        text = "${attendee.title} · ${attendee.company}",
                        fontSize = 12.sp,
                        color = Secondary,
                        maxLines = 1
                    )
                }

                // Status or Fit
                Surface(
                    color = Color(0xFFE6F7ED),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = "${attendee.matchScore}% FIT",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF006948),
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                    )
                }
            }

            // Relationship Reason ("Why meet?")
            Surface(
                color = SurfaceContainerLow,
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "Why meet?",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Primary,
                        letterSpacing = 0.5.sp
                    )
                    Text(
                        text = attendee.whyMeetReason,
                        fontSize = 13.sp,
                        color = OnSurface,
                        lineHeight = 18.sp
                    )
                }
            }

            // Location & CTA Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                StatusPill(text = attendee.status, isLive = attendee.isAvailable)

                if (attendee.connectionStatus == ConnectionStatus.REQUESTED) {
                    Surface(
                        color = SecondaryContainer,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Schedule,
                                contentDescription = null,
                                tint = Primary,
                                modifier = Modifier.size(14.dp)
                            )
                            Text(
                                text = "Request Sent",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Primary
                            )
                        }
                    }
                } else {
                    Button(
                        onClick = onRequestMeeting,
                        colors = ButtonDefaults.buttonColors(containerColor = Primary),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp),
                        modifier = Modifier
                            .height(36.dp)
                            .testTag("request_meeting_button_${attendee.id}")
                    ) {
                        Icon(
                            imageVector = Icons.Default.CalendarToday,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Request meeting",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ActiveMeetingCountdownCard(
    meeting: com.example.data.ScheduledMeeting,
    onOpenProfile: () -> Unit,
    onImHere: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFF151D21)),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
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
                            .background(Color(0xFF00E676))
                    )
                    Text(
                        text = "YOUR NEXT MEETING",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF00E676),
                        letterSpacing = 0.5.sp
                    )
                }

                Surface(
                    color = Color(0xFF2C393F),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = meeting.timeSlot,
                        fontSize = 11.sp,
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }
            }

            // Big countdown
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Starts in",
                        fontSize = 12.sp,
                        color = Color(0xFF90A4AE)
                    )
                    Text(
                        text = "08:42",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Location:",
                        fontSize = 11.sp,
                        color = Color(0xFF90A4AE)
                    )
                    Text(
                        text = meeting.location,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryFixed
                    )
                }
            }

            HorizontalDivider(color = Color(0xFF2A363C))

            // Peer info & actions
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(meeting.attendee.avatarUrl)
                                .crossfade(true)
                                .build(),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    Text(
                        text = meeting.attendee.name,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedButton(
                        onClick = onOpenProfile,
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                        modifier = Modifier.height(32.dp)
                    ) {
                        Text("Open profile", fontSize = 11.sp)
                    }

                    Button(
                        onClick = onImHere,
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00A86B)),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                        modifier = Modifier.height(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(13.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("I'm here", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun IncomingRequestAlertCard(
    meeting: com.example.data.ScheduledMeeting,
    onAccept: () -> Unit,
    onDecline: () -> Unit,
    onOpenProfile: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(1.5.dp, PrimaryFixed),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
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
                            .background(Primary)
                    )
                    Text(
                        text = "INCOMING MEETING REQUEST",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Primary,
                        letterSpacing = 0.5.sp
                    )
                }

                Surface(
                    color = SecondaryContainer,
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = meeting.timeSlot,
                        fontSize = 11.sp,
                        color = Primary,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(meeting.attendee.avatarUrl)
                            .crossfade(true)
                            .build(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Column {
                    Text(
                        text = "${meeting.attendee.name} wants to meet",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = OnSurface
                    )
                    Text(
                        text = "${meeting.attendee.title} · ${meeting.attendee.company}",
                        fontSize = 11.sp,
                        color = Secondary
                    )
                }
            }

            if (meeting.note != null) {
                Surface(
                    color = SurfaceContainerLow,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "\"${meeting.note}\"",
                        fontSize = 12.sp,
                        color = OnSurfaceVariant,
                        modifier = Modifier.padding(10.dp)
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedButton(
                    onClick = onDecline,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .weight(1f)
                        .height(36.dp)
                ) {
                    Text("Decline", fontSize = 12.sp)
                }

                OutlinedButton(
                    onClick = onOpenProfile,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .weight(1f)
                        .height(36.dp)
                ) {
                    Text("Profile", fontSize = 12.sp)
                }

                Button(
                    onClick = onAccept,
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Primary),
                    modifier = Modifier
                        .weight(1.2f)
                        .height(36.dp)
                ) {
                    Text("Accept", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
