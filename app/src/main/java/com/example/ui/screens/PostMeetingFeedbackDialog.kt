package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import com.example.data.OrbitRepository
import com.example.data.ScheduledMeeting
import com.example.ui.theme.*

@Composable
fun PostMeetingFeedbackDialog(
    meeting: ScheduledMeeting,
    onDismiss: () -> Unit,
    onSubmitFeedback: (rating: String, connectionType: String) -> Unit
) {
    val ratingOptions = listOf(
        "Very relevant",
        "Somewhat relevant",
        "Not relevant"
    )

    val connectionTypes = listOf(
        "Customer",
        "Cofounder",
        "Investor",
        "Technical Peer",
        "Friend / General"
    )

    var selectedRating by remember { mutableStateOf(ratingOptions[0]) }
    var selectedType by remember { mutableStateOf(connectionTypes[0]) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Column {
                Text(
                    text = "Meeting Feedback",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = OnSurface
                )
                Text(
                    text = "How was your conversation with ${meeting.attendee.name}?",
                    fontSize = 13.sp,
                    color = Secondary
                )
            }
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Peer preview
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
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
                            text = meeting.attendee.name,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "${meeting.attendee.title} · ${meeting.attendee.company}",
                            fontSize = 12.sp,
                            color = Secondary
                        )
                    }
                }

                // Question 1: Relevance
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "1. How relevant was this match?",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = OnSurface
                    )

                    ratingOptions.forEach { option ->
                        val isSelected = selectedRating == option
                        Surface(
                            color = if (isSelected) PrimaryFixed else SurfaceContainerLowest,
                            shape = RoundedCornerShape(8.dp),
                            border = if (isSelected) androidx.compose.foundation.BorderStroke(1.5.dp, Primary)
                            else androidx.compose.foundation.BorderStroke(1.dp, OutlineVariant),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { selectedRating = option }
                                .testTag("rating_$option")
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                RadioButton(
                                    selected = isSelected,
                                    onClick = { selectedRating = option },
                                    colors = RadioButtonDefaults.colors(selectedColor = Primary)
                                )
                                Text(
                                    text = option,
                                    fontSize = 13.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    color = if (isSelected) Primary else OnSurface
                                )
                            }
                        }
                    }
                }

                // Question 2: Connection Type
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "2. What type of connection did this become?",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = OnSurface
                    )

                    connectionTypes.forEach { type ->
                        val isSelected = selectedType == type
                        Surface(
                            color = if (isSelected) SecondaryContainer else SurfaceContainerLowest,
                            shape = RoundedCornerShape(8.dp),
                            border = if (isSelected) androidx.compose.foundation.BorderStroke(1.5.dp, Primary)
                            else androidx.compose.foundation.BorderStroke(1.dp, OutlineVariant),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { selectedType = type }
                                .testTag("type_$type")
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                RadioButton(
                                    selected = isSelected,
                                    onClick = { selectedType = type },
                                    colors = RadioButtonDefaults.colors(selectedColor = Primary)
                                )
                                Text(
                                    text = type,
                                    fontSize = 13.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    color = OnSurface
                                )
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    OrbitRepository.submitMeetingFeedback(
                        meetingId = meeting.id,
                        rating = selectedRating,
                        connectionType = selectedType
                    )
                    onSubmitFeedback(selectedRating, selectedType)
                },
                colors = ButtonDefaults.buttonColors(containerColor = Primary),
                modifier = Modifier.testTag("submit_feedback_button")
            ) {
                Text("Save Feedback", fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Later", color = Secondary)
            }
        }
    )
}
