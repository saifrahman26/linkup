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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.Attendee
import com.example.data.OrbitRepository
import com.example.data.ScheduledMeeting
import com.example.ui.theme.*

@Composable
fun MeetingRequestDialog(
    attendee: Attendee,
    onDismiss: () -> Unit,
    onRequestSent: (timeSlot: String, location: String, note: String) -> Unit
) {
    val availableTimeSlots = listOf(
        "11:30–11:45",
        "12:15–12:30",
        "2:00–2:15"
    )
    val locations = listOf(
        "Lounge A",
        "Networking Zone",
        "Coffee Area"
    )

    var selectedTimeSlot by remember { mutableStateOf(availableTimeSlots[0]) }
    var selectedLocation by remember { mutableStateOf(locations[0]) }
    var personalNote by remember { mutableStateOf("Looking forward to discussing agent infrastructure & potential collaboration!") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Column {
                Text(
                    text = "Request a meeting",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = OnSurface
                )
                Text(
                    text = "with ${attendee.name}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Secondary
                )
            }
        },
        text = {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Time Slot Section
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            text = "${attendee.name} is available:",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = OnSurface
                        )

                        availableTimeSlots.forEach { slot ->
                            val isSelected = selectedTimeSlot == slot
                            Surface(
                                color = if (isSelected) PrimaryFixed else SurfaceContainerLowest,
                                shape = RoundedCornerShape(10.dp),
                                border = if (isSelected) androidx.compose.foundation.BorderStroke(1.5.dp, Primary)
                                else androidx.compose.foundation.BorderStroke(1.dp, OutlineVariant),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { selectedTimeSlot = slot }
                                    .testTag("slot_$slot")
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    RadioButton(
                                        selected = isSelected,
                                        onClick = { selectedTimeSlot = slot },
                                        colors = RadioButtonDefaults.colors(selectedColor = Primary)
                                    )
                                    Text(
                                        text = slot,
                                        fontSize = 14.sp,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                        color = if (isSelected) Primary else OnSurface
                                    )
                                }
                            }
                        }
                    }
                }

                // Location Selection
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            text = "Where to meet:",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = OnSurface
                        )

                        locations.forEach { loc ->
                            val isSelected = selectedLocation == loc
                            Surface(
                                color = if (isSelected) SecondaryContainer else SurfaceContainerLowest,
                                shape = RoundedCornerShape(10.dp),
                                border = if (isSelected) androidx.compose.foundation.BorderStroke(1.5.dp, Primary)
                                else androidx.compose.foundation.BorderStroke(1.dp, OutlineVariant),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { selectedLocation = loc }
                                    .testTag("location_$loc")
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    Icon(
                                        imageVector = when (loc) {
                                            "Coffee Area" -> Icons.Default.Coffee
                                            "Networking Zone" -> Icons.Default.Groups
                                            else -> Icons.Default.Weekend
                                        },
                                        contentDescription = null,
                                        tint = if (isSelected) Primary else Secondary,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Text(
                                        text = loc,
                                        fontSize = 14.sp,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                        color = OnSurface
                                    )
                                }
                            }
                        }
                    }
                }

                // Optional Quick Note
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(
                            text = "Quick Context (Optional):",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = Secondary
                        )
                        OutlinedTextField(
                            value = personalNote,
                            onValueChange = { personalNote = it },
                            placeholder = { Text("Topic or intent...") },
                            maxLines = 2,
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp)
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    OrbitRepository.requestMeeting(
                        attendee = attendee,
                        timeSlot = selectedTimeSlot,
                        location = selectedLocation,
                        note = personalNote
                    )
                    onRequestSent(selectedTimeSlot, selectedLocation, personalNote)
                },
                colors = ButtonDefaults.buttonColors(containerColor = Primary),
                modifier = Modifier.testTag("confirm_request_meeting_button")
            ) {
                Text("Send Request", fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                modifier = Modifier.testTag("cancel_request_meeting_button")
            ) {
                Text("Cancel", color = Secondary)
            }
        }
    )
}

@Composable
fun MeetingRequestSentDialog(
    attendeeName: String,
    timeSlot: String,
    location: String,
    onViewSchedule: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF00A86B)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                }
                Text(
                    text = "Meeting request sent",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = OnSurface
                )
            }
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    text = "Waiting for $attendeeName to accept.",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Primary
                )

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
                            text = "Proposed slot: $timeSlot",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            color = OnSurface
                        )
                        Text(
                            text = "Meeting spot: $location",
                            fontSize = 13.sp,
                            color = Secondary
                        )
                    }
                }

                Text(
                    text = "You'll receive a push notification immediately once confirmed with calendar reminder and countdown.",
                    fontSize = 12.sp,
                    color = Secondary,
                    lineHeight = 16.sp
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onDismiss()
                    onViewSchedule()
                },
                colors = ButtonDefaults.buttonColors(containerColor = Primary),
                modifier = Modifier.testTag("view_today_schedule_button")
            ) {
                Text("View Schedule", fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Back to Plan", color = Secondary)
            }
        }
    )
}
