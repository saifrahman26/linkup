package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import com.example.ui.components.StatusPill
import com.example.ui.theme.*

@Composable
fun ProfileScreen(
    onSignOut: () -> Unit,
    onOrganizerDashboard: () -> Unit,
    modifier: Modifier = Modifier
) {
    val user = OrbitRepository.currentUser
    var isVisibleInRadar by remember { mutableStateOf(true) }
    var allowDirectIntros by remember { mutableStateOf(true) }
    var shareLinkedIn by remember { mutableStateOf(true) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Attendee Digital Pass Card
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            color = PrimaryFixed,
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = "TECH SUMMIT 2026",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = Primary,
                                letterSpacing = 0.5.sp,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }

                        Text(
                            text = "PASS #8491",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Secondary
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .clip(CircleShape)
                                .border(2.dp, Primary, CircleShape)
                        ) {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(user.avatarUrl)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = user.name,
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
                                    text = user.name,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = OnSurface
                                )
                                Icon(
                                    imageVector = Icons.Default.Verified,
                                    contentDescription = "Verified Delegate",
                                    tint = Primary,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                            Text(
                                text = "${user.title} · ${user.company}",
                                fontSize = 13.sp,
                                color = Secondary
                            )
                            Text(
                                text = user.location,
                                fontSize = 11.sp,
                                color = Secondary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    StatusPill(text = user.status, isLive = true)
                }
            }
        }

        // What I'm Building
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.RocketLaunch,
                            contentDescription = null,
                            tint = Primary,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "WHAT I'M BUILDING",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Primary,
                            letterSpacing = 0.5.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = user.building,
                        fontSize = 13.sp,
                        color = OnSurface,
                        lineHeight = 18.sp
                    )
                }
            }
        }

        // What I'm Looking For
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            tint = Primary,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "WHAT I'M LOOKING FOR TODAY",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Primary,
                            letterSpacing = 0.5.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = user.lookingFor,
                        fontSize = 13.sp,
                        color = OnSurface,
                        lineHeight = 18.sp
                    )
                }
            }
        }

        // What I Can Help With
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.VolunteerActivism,
                            contentDescription = null,
                            tint = Color(0xFF00A86B),
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "HOW I CAN HELP OTHERS",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF006948),
                            letterSpacing = 0.5.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        user.canHelpWith.forEach { tag ->
                            Surface(
                                color = Color(0xFFE6F7ED),
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Text(
                                    text = tag,
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

        // Privacy & Radar Settings
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "EVENT PRIVACY & RADAR CONTROLS",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Secondary,
                        letterSpacing = 0.5.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    SettingToggleRow(
                        title = "Visible in Discover Radar",
                        subtitle = "Allow other attendees in the venue to see your profile card",
                        checked = isVisibleInRadar,
                        onCheckedChange = { isVisibleInRadar = it }
                    )

                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = OutlineVariant)

                    SettingToggleRow(
                        title = "Accept Direct In-Person Intros",
                        subtitle = "Enable 45-min timed invitations in Orbit Lounge",
                        checked = allowDirectIntros,
                        onCheckedChange = { allowDirectIntros = it }
                    )

                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = OutlineVariant)

                    SettingToggleRow(
                        title = "Share LinkedIn upon Mutual Accept",
                        subtitle = "Automatically exchange full profiles when both accept",
                        checked = shareLinkedIn,
                        onCheckedChange = { shareLinkedIn = it }
                    )
                }
            }
        }

        // Actions
        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                FilledTonalButton(
                    onClick = onOrganizerDashboard,
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.filledTonalButtonColors(
                        containerColor = SurfaceContainerHigh,
                        contentColor = Primary
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("profile_organizer_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Analytics,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("View Live Organizer Dashboard", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                }

                OutlinedButton(
                    onClick = onSignOut,
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Error),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("profile_sign_out_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Logout,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp),
                        tint = Error
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Sign Out / Switch Account", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}

@Composable
private fun SettingToggleRow(
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = OnSurface
            )
            Text(
                text = subtitle,
                fontSize = 11.sp,
                color = Secondary,
                lineHeight = 15.sp
            )
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = Primary
            )
        )
    }
}
