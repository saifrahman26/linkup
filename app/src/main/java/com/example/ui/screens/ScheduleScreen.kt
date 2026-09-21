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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.data.Attendee
import com.example.data.MeetingStatus
import com.example.data.OrbitRepository
import com.example.data.ScheduledMeeting
import com.example.ui.components.OrbitHeader
import com.example.ui.theme.*

@Composable
fun ScheduleScreen(
    onAttendeeClick: (Attendee) -> Unit,
    onFeedbackClick: (ScheduledMeeting) -> Unit,
    onEndEventRecapClick: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val meetings by OrbitRepository.meetings.collectAsState()

    // Agenda / Keynote timeline items for full context
    val eventTimeline = listOf(
        TimelineItem("9:30 AM", "Badge Scan & Keynote Entry", "Main Hall", true),
        TimelineItem("10:00 AM", "Keynote: Foundation Models & Autonomy", "Stage A", true),
        TimelineItem("12:00 PM", "Community Lunch & Hallway Track", "Orbit Lounge Level 2", false),
        TimelineItem("2:30 PM", "Scaling Inference Pipelines w/ Aisha Khan", "Room 302", false),
        TimelineItem("5:00 PM", "Founder & Investor Closing Mixer", "VIP Terrace", false)
    )

    Scaffold(
        topBar = {
            OrbitHeader(
                title = "YOUR DAY",
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
            contentPadding = PaddingValues(top = 16.dp, bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header summary
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
                                text = "Today's Schedule",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = OnSurface
                            )
                            Surface(
                                color = Color(0xFFE6F7ED),
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Text(
                                    text = "DAY 2 · ACTIVE",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF006948),
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Meetings, confirmed slots, and scheduled talks at Tech Summit '26.",
                            fontSize = 12.sp,
                            color = Secondary
                        )
                    }
                }
            }

            // Quick Banner for End of Event Recap
            item {
                Surface(
                    color = PrimaryFixed,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onEndEventRecapClick() }
                        .testTag("open_event_recap_banner")
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Summarize,
                                contentDescription = null,
                                tint = Primary,
                                modifier = Modifier.size(22.dp)
                            )
                            Column {
                                Text(
                                    text = "End-of-Event Recap Ready",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Primary
                                )
                                Text(
                                    text = "Review recommended, meetings, connections & follow-ups",
                                    fontSize = 11.sp,
                                    color = OnSurfaceVariant
                                )
                            }
                        }

                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = null,
                            tint = Primary
                        )
                    }
                }
            }

            // Section 1: Confirmed & Pending Meetings
            item {
                Text(
                    text = "YOUR 1-ON-1 MEETINGS",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Secondary,
                    letterSpacing = 0.5.sp
                )
            }

            items(meetings, key = { it.id }) { meeting ->
                ScheduledMeetingItemCard(
                    meeting = meeting,
                    onClick = { onAttendeeClick(meeting.attendee) },
                    onFeedbackClick = { onFeedbackClick(meeting) }
                )
            }

            // Section 2: Summit Keynotes & Sessions
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "SUMMIT PROGRAM SESSIONS",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Secondary,
                    letterSpacing = 0.5.sp
                )
            }

            items(eventTimeline) { item ->
                SummitTimelineRow(item)
            }
        }
    }
}

data class TimelineItem(
    val time: String,
    val title: String,
    val location: String,
    val isCompleted: Boolean
)

@Composable
fun ScheduledMeetingItemCard(
    meeting: ScheduledMeeting,
    onClick: () -> Unit,
    onFeedbackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.5.dp),
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    color = when (meeting.status) {
                        MeetingStatus.ACCEPTED_SCHEDULED -> Color(0xFFE6F7ED)
                        MeetingStatus.INCOMING_REQUEST -> PrimaryFixed
                        MeetingStatus.COMPLETED_NEEDS_FEEDBACK -> SecondaryContainer
                        MeetingStatus.FEEDBACK_SUBMITTED -> SurfaceContainerHigh
                        else -> SurfaceContainerHigh
                    },
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = meeting.timeSlot,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = when (meeting.status) {
                            MeetingStatus.ACCEPTED_SCHEDULED -> Color(0xFF006948)
                            MeetingStatus.INCOMING_REQUEST -> Primary
                            else -> OnSurface
                        },
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }

                Text(
                    text = when (meeting.status) {
                        MeetingStatus.ACCEPTED_SCHEDULED -> "Confirmed"
                        MeetingStatus.PENDING_RESPONSE -> "Pending Response"
                        MeetingStatus.INCOMING_REQUEST -> "Action Needed"
                        MeetingStatus.COMPLETED_NEEDS_FEEDBACK -> "Feedback Needed"
                        MeetingStatus.FEEDBACK_SUBMITTED -> "Completed"
                        MeetingStatus.IN_PROGRESS -> "In Progress"
                        MeetingStatus.DECLINED -> "Declined"
                    },
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = when (meeting.status) {
                        MeetingStatus.ACCEPTED_SCHEDULED -> Color(0xFF006948)
                        MeetingStatus.COMPLETED_NEEDS_FEEDBACK -> Primary
                        else -> Secondary
                    }
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
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

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = meeting.attendee.name,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = OnSurface
                    )
                    Text(
                        text = "${meeting.attendee.title} · ${meeting.attendee.company}",
                        fontSize = 12.sp,
                        color = Secondary
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.padding(top = 2.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Place,
                            contentDescription = null,
                            tint = Secondary,
                            modifier = Modifier.size(12.dp)
                        )
                        Text(
                            text = meeting.location,
                            fontSize = 11.sp,
                            color = Secondary
                        )
                    }
                }

                if (meeting.status == MeetingStatus.COMPLETED_NEEDS_FEEDBACK) {
                    Button(
                        onClick = onFeedbackClick,
                        colors = ButtonDefaults.buttonColors(containerColor = Primary),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                        modifier = Modifier.height(32.dp)
                    ) {
                        Text("Rate", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun SummitTimelineRow(item: TimelineItem) {
    Surface(
        color = SurfaceContainerLowest,
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Surface(
                color = SurfaceContainerHigh,
                shape = RoundedCornerShape(6.dp)
            ) {
                Text(
                    text = item.time,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = OnSurface,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.title,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = OnSurface
                )
                Text(
                    text = item.location,
                    fontSize = 11.sp,
                    color = Secondary
                )
            }

            if (item.isCompleted) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = Color(0xFF00A86B),
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}
