package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import com.example.data.OrbitRepository
import com.example.ui.theme.*

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ConnectionRequestDialog(
    attendee: Attendee,
    onDismiss: () -> Unit,
    onSent: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedContext by remember { mutableStateOf("Grab quick coffee now") }
    val contextOptions = listOf(
        "Grab quick coffee now",
        "Discuss Co-founder fit",
        "Compare notes on Infra",
        "Exchange info for post-event"
    )

    var attachLocation by remember { mutableStateOf(true) }
    var noteText by remember {
        mutableStateOf("Hey ${attendee.name.split(" ").first()}, saw your work in ${attendee.company}. I'm building Vesper AI and would love to chat about scaling agent infrastructure over coffee in the Lounge.")
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        modifier = modifier.fillMaxWidth(),
        properties = androidx.compose.ui.window.DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = SurfaceContainerLowest,
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .wrapContentHeight()
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Connection Request",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = OnSurface
                    )
                    IconButton(onClick = onDismiss, modifier = Modifier.size(32.dp)) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                    }
                }

                // Proximity Match Banner
                Surface(
                    color = Color(0xFFE6F7ED),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF00A86B))
                        )
                        Text(
                            text = "In-person proximity match · 45m active window",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF006948)
                        )
                    }
                }

                // Recipient Card
                Card(
                    colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .border(1.5.dp, Primary, CircleShape)
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

                        Column {
                            Text(
                                text = attendee.name,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = OnSurface
                            )
                            Text(
                                text = "${attendee.title} · ${attendee.company}",
                                fontSize = 12.sp,
                                color = Secondary
                            )
                            Surface(
                                color = SecondaryContainer,
                                shape = RoundedCornerShape(4.dp),
                                modifier = Modifier.padding(top = 2.dp)
                            ) {
                                Text(
                                    text = "Strong Match: ${attendee.matchScore}% Fit",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Primary,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }
                }

                // Context Options
                Text(
                    text = "PRIMARY CONTEXT FOR CONNECTING",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Secondary,
                    letterSpacing = 0.5.sp
                )

                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    contextOptions.forEach { opt ->
                        val isSelected = selectedContext == opt
                        FilterChip(
                            selected = isSelected,
                            onClick = { selectedContext = opt },
                            label = { Text(opt, fontSize = 11.sp) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = Primary,
                                selectedLabelColor = Color.White,
                                containerColor = SurfaceContainerLowest,
                                labelColor = OnSurface
                            ),
                            shape = RoundedCornerShape(16.dp)
                        )
                    }
                }

                // Location Suggestion
                Card(
                    colors = CardDefaults.cardColors(containerColor = SurfaceContainerHigh),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Place,
                                contentDescription = null,
                                tint = Primary,
                                modifier = Modifier.size(18.dp)
                            )
                            Column {
                                Text(
                                    text = "Suggested Spot",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Secondary
                                )
                                Text(
                                    text = "Orbit Lounge · Level 2",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = OnSurface
                                )
                            }
                        }

                        Switch(
                            checked = attachLocation,
                            onCheckedChange = { attachLocation = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = Primary
                            )
                        )
                    }
                }

                // Note Field
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "PERSONALIZED NOTE",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Secondary,
                            letterSpacing = 0.5.sp
                        )
                        Text(
                            text = "${noteText.length}/300",
                            fontSize = 11.sp,
                            color = Secondary
                        )
                    }

                    OutlinedTextField(
                        value = noteText,
                        onValueChange = { if (it.length <= 300) noteText = it },
                        minLines = 3,
                        maxLines = 4,
                        shape = RoundedCornerShape(10.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Primary,
                            unfocusedBorderColor = OutlineVariant
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("connection_note_input")
                    )
                }

                // Footnote
                Text(
                    text = "Both attendees must accept to share direct contact passes. We respect attendee signal & focus.",
                    fontSize = 11.sp,
                    color = Secondary,
                    lineHeight = 14.sp
                )

                // Actions
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                    ) {
                        Text("Cancel")
                    }

                    Button(
                        onClick = {
                            OrbitRepository.sendConnectionRequest(
                                attendeeId = attendee.id,
                                note = noteText,
                                meetLocation = if (attachLocation) "Orbit Lounge Level 2" else "Moscone West"
                            )
                            onSent()
                        },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Primary),
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .testTag("confirm_send_request_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Bolt,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Send Request", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
