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
import com.example.data.NotificationAction
import com.example.data.NotificationItem
import com.example.data.OrbitRepository
import com.example.ui.theme.*

@Composable
fun AlertsScreen(
    onNavigateToNetwork: () -> Unit,
    onNavigateToDiscover: () -> Unit,
    modifier: Modifier = Modifier
) {
    val notifications by OrbitRepository.notifications.collectAsState()
    var selectedFilter by remember { mutableStateOf("All") }
    val filters = listOf("All", "Requests", "Matches")

    val filteredNotifications = remember(notifications, selectedFilter) {
        when (selectedFilter) {
            "Requests" -> notifications.filter { it.category == "requests" }
            "Matches" -> notifications.filter { it.category == "matches" }
            else -> notifications
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Top Action Row
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Notification Center",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = OnSurface
                )

                TextButton(
                    onClick = { OrbitRepository.markAllNotificationsRead() },
                    modifier = Modifier.testTag("mark_all_read_button")
                ) {
                    Text("Mark all read", fontSize = 12.sp, color = Primary, fontWeight = FontWeight.SemiBold)
                }
            }
        }

        // Filters
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
                        shape = RoundedCornerShape(20.dp)
                    )
                }
            }
        }

        // Notifications List
        items(filteredNotifications, key = { it.id }) { notif ->
            NotificationCard(
                notif = notif,
                onActionClick = {
                    when (notif.actionType) {
                        NotificationAction.ACCEPT_DECLINE -> onNavigateToNetwork()
                        NotificationAction.VIEW_RECOMMENDATIONS -> onNavigateToDiscover()
                        else -> onNavigateToNetwork()
                    }
                }
            )
        }

        // Preferences Card
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
                        imageVector = Icons.Default.NotificationsActive,
                        contentDescription = null,
                        tint = Primary,
                        modifier = Modifier.size(20.dp)
                    )
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Live In-Person Proximity Pings",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = OnSurface
                        )
                        Text(
                            text = "Alerts trigger when a high-signal match enters Orbit Lounge Level 2.",
                            fontSize = 11.sp,
                            color = Secondary
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun NotificationCard(
    notif: NotificationItem,
    onActionClick: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = if (notif.isUnread) SurfaceContainerLowest else SurfaceContainerLow
        ),
        shape = RoundedCornerShape(14.dp),
        border = if (notif.isUnread) {
            androidx.compose.foundation.BorderStroke(1.dp, PrimaryFixed)
        } else null,
        elevation = CardDefaults.cardElevation(defaultElevation = if (notif.isUnread) 2.dp else 0.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onActionClick)
            .testTag("notification_${notif.id}")
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (notif.avatarUrl != null) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .border(1.5.dp, PrimaryFixed, CircleShape)
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(notif.avatarUrl)
                            .crossfade(true)
                            .build(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            } else {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(PrimaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Bolt,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = notif.title,
                        fontSize = 14.sp,
                        fontWeight = if (notif.isUnread) FontWeight.Bold else FontWeight.SemiBold,
                        color = OnSurface,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = notif.timeAgo,
                        fontSize = 11.sp,
                        color = Secondary
                    )
                }

                Text(
                    text = notif.subtitle,
                    fontSize = 12.sp,
                    color = Secondary,
                    lineHeight = 16.sp,
                    modifier = Modifier.padding(top = 2.dp)
                )

                if (notif.quote != null) {
                    Surface(
                        color = SurfaceContainerHigh,
                        shape = RoundedCornerShape(6.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 6.dp)
                    ) {
                        Text(
                            text = notif.quote,
                            fontSize = 11.sp,
                            color = OnSurfaceVariant,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    FilledTonalButton(
                        onClick = onActionClick,
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                        colors = ButtonDefaults.filledTonalButtonColors(
                            containerColor = if (notif.isUnread) Primary else SurfaceContainerHighest,
                            contentColor = if (notif.isUnread) Color.White else Primary
                        ),
                        modifier = Modifier.height(32.dp)
                    ) {
                        Text(
                            text = when (notif.actionType) {
                                NotificationAction.ACCEPT_DECLINE -> "Respond →"
                                NotificationAction.VIEW_RECOMMENDATIONS -> "View Matches →"
                                NotificationAction.REPLY -> "Message →"
                                else -> "View →"
                            },
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
