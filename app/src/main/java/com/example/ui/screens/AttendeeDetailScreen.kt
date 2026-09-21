package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
fun AttendeeDetailScreen(
    attendee: Attendee,
    onBack: () -> Unit,
    onSendConnectionRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isSaved by remember { mutableStateOf(attendee.isSaved) }

    Scaffold(
        topBar = {
            Surface(
                color = SurfaceContainerLowest,
                shadowElevation = 1.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(onClick = onBack, modifier = Modifier.testTag("detail_back_button")) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }

                    Text(
                        text = "Attendee Profile",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = OnSurface
                    )

                    Row {
                        IconButton(onClick = {
                            isSaved = !isSaved
                            OrbitRepository.toggleSaveAttendee(attendee.id)
                        }) {
                            Icon(
                                imageVector = if (isSaved) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                                contentDescription = "Save",
                                tint = if (isSaved) Primary else OnSurface
                            )
                        }
                    }
                }
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
                        .padding(horizontal = 20.dp, vertical = 12.dp)
                ) {
                    when (attendee.connectionStatus) {
                        ConnectionStatus.CONNECTED -> {
                            Button(
                                onClick = {},
                                enabled = false,
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE6F7ED)),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                    tint = Color(0xFF006948)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Connected · Active in Network",
                                    color = Color(0xFF006948),
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                        ConnectionStatus.REQUESTED -> {
                            Button(
                                onClick = {},
                                enabled = false,
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp)
                            ) {
                                Text("Intro Request Pending", fontWeight = FontWeight.Bold)
                            }
                        }
                        else -> {
                            Button(
                                onClick = onSendConnectionRequest,
                                colors = ButtonDefaults.buttonColors(containerColor = Primary),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp)
                                    .testTag("detail_connect_button")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Bolt,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Send Connection Request ⚡",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
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
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(top = 16.dp, bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Profile Card Header
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(72.dp)
                                    .clip(CircleShape)
                                    .border(2.5.dp, Primary, CircleShape)
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
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Text(
                                        text = attendee.name,
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = OnSurface
                                    )
                                    if (attendee.verified) {
                                        Icon(
                                            imageVector = Icons.Default.Verified,
                                            contentDescription = "Verified Delegate",
                                            tint = Primary,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                }
                                Text(
                                    text = "${attendee.title} · ${attendee.company}",
                                    fontSize = 13.sp,
                                    color = Secondary
                                )
                                Text(
                                    text = attendee.location,
                                    fontSize = 12.sp,
                                    color = Secondary
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            StatusPill(text = attendee.status, isLive = attendee.isAvailable)
                            MatchScoreBadge(score = attendee.matchScore)
                        }
                    }
                }
            }

            // Why You Should Meet
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F5FF)),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = null,
                                tint = Primary,
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                text = "WHY YOU SHOULD MEET AT THIS EVENT",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Primary,
                                letterSpacing = 0.5.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = attendee.whyMeetReason,
                            fontSize = 13.sp,
                            lineHeight = 18.sp,
                            color = OnSurface,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            // What they are building
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "WHAT THEY ARE BUILDING",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Secondary,
                            letterSpacing = 0.5.sp
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = attendee.building,
                            fontSize = 13.sp,
                            lineHeight = 18.sp,
                            color = OnSurface
                        )
                    }
                }
            }

            // What they are looking for
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "LOOKING FOR TODAY",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Secondary,
                            letterSpacing = 0.5.sp
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = attendee.lookingFor,
                            fontSize = 13.sp,
                            lineHeight = 18.sp,
                            color = OnSurface
                        )
                    }
                }
            }

            // What they can help with
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "OFFERING / CAN HELP WITH",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF006948),
                            letterSpacing = 0.5.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            attendee.canHelpWith.forEach { item ->
                                Surface(
                                    color = Color(0xFFE6F7ED),
                                    shape = RoundedCornerShape(6.dp)
                                ) {
                                    Text(
                                        text = item,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color(0xFF006948),
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Talks & Sessions
            if (attendee.recentTalk.isNotEmpty()) {
                item {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Mic,
                                contentDescription = null,
                                tint = Primary,
                                modifier = Modifier.size(20.dp)
                            )
                            Column {
                                Text(
                                    text = "CONFERENCE SESSION",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Secondary,
                                    letterSpacing = 0.5.sp
                                )
                                Text(
                                    text = attendee.recentTalk,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = OnSurface
                                )
                            }
                        }
                    }
                }
            }

            // Mutual Event Context
            item {
                Surface(
                    color = SurfaceContainerLow,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Place,
                            contentDescription = null,
                            tint = Secondary,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = attendee.mutualContext,
                            fontSize = 12.sp,
                            color = Secondary
                        )
                    }
                }
            }
        }
    }
}
