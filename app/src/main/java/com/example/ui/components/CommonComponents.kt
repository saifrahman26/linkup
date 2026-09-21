package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.ui.theme.*

@Composable
fun OrbitHeader(
    modifier: Modifier = Modifier,
    title: String = "TECHSUMMIT '26",
    subtitle: String? = null,
    showBack: Boolean = false,
    onBack: (() -> Unit)? = null,
    onOrganizerClick: (() -> Unit)? = null,
    onProfileClick: (() -> Unit)? = null,
    profileAvatarUrl: String? = null,
    showOrganizerBadge: Boolean = true
) {
    Surface(
        color = SurfaceContainerLowest,
        shadowElevation = 1.dp,
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                if (showBack && onBack != null) {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier
                            .size(36.dp)
                            .testTag("header_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = OnSurface
                        )
                    }
                }

                // Orbit Logo Symbol
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Primary),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.AllInclusive,
                        contentDescription = "Orbit Logo",
                        tint = Color.White,
                        modifier = Modifier.size(22.dp)
                    )
                }

                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = "Orbit",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = OnSurface
                        )
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(PrimaryFixed)
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = title,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = Primary,
                                letterSpacing = 0.5.sp
                            )
                        }
                    }
                    if (subtitle != null) {
                        Text(
                            text = subtitle,
                            fontSize = 11.sp,
                            color = Secondary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (showOrganizerBadge && onOrganizerClick != null) {
                    FilledTonalButton(
                        onClick = onOrganizerClick,
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                        colors = ButtonDefaults.filledTonalButtonColors(
                            containerColor = SurfaceContainerHigh,
                            contentColor = Primary
                        ),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .height(34.dp)
                            .testTag("header_organizer_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Analytics,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Organizers",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                if (profileAvatarUrl != null && onProfileClick != null) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .border(1.5.dp, Primary, CircleShape)
                            .clickable(onClick = onProfileClick)
                            .testTag("header_profile_avatar")
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(profileAvatarUrl)
                                .crossfade(true)
                                .build(),
                            contentDescription = "Profile",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun StatusPill(
    text: String,
    modifier: Modifier = Modifier,
    isLive: Boolean = true
) {
    Surface(
        color = if (isLive) Color(0xFFE6F7ED) else SurfaceContainer,
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(7.dp)
                    .clip(CircleShape)
                    .background(if (isLive) Color(0xFF00A86B) else Secondary)
            )
            Text(
                text = text,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                color = if (isLive) Color(0xFF006948) else OnSurfaceVariant
            )
        }
    }
}

@Composable
fun MatchScoreBadge(
    score: Int,
    modifier: Modifier = Modifier
) {
    Surface(
        color = PrimaryContainer,
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Bolt,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(13.dp)
            )
            Text(
                text = "$score% MATCH",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 11.sp,
                letterSpacing = 0.5.sp
            )
        }
    }
}

@Composable
fun BottomNavigationTabs(
    selectedTab: String,
    onTabSelected: (String) -> Unit,
    pendingRequestsCount: Int = 2,
    unreadNotificationsCount: Int = 2,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        containerColor = SurfaceContainerLowest,
        tonalElevation = 8.dp,
        modifier = modifier.fillMaxWidth()
    ) {
        NavigationBarItem(
            selected = selectedTab == "discover",
            onClick = { onTabSelected("discover") },
            icon = {
                Icon(
                    imageVector = if (selectedTab == "discover") Icons.Filled.AssignmentInd else Icons.Outlined.AssignmentInd,
                    contentDescription = "Plan"
                )
            },
            label = { Text("Plan", fontSize = 11.sp, fontWeight = FontWeight.SemiBold) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Primary,
                selectedTextColor = Primary,
                indicatorColor = SecondaryContainer
            ),
            modifier = Modifier.testTag("nav_tab_plan")
        )

        NavigationBarItem(
            selected = selectedTab == "people",
            onClick = { onTabSelected("people") },
            icon = {
                Icon(
                    imageVector = if (selectedTab == "people") Icons.Filled.Groups else Icons.Outlined.Groups,
                    contentDescription = "People"
                )
            },
            label = { Text("People", fontSize = 11.sp) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Primary,
                selectedTextColor = Primary,
                indicatorColor = SecondaryContainer
            ),
            modifier = Modifier.testTag("nav_tab_people")
        )

        NavigationBarItem(
            selected = selectedTab == "network",
            onClick = { onTabSelected("network") },
            icon = {
                BadgedBox(
                    badge = {
                        if (pendingRequestsCount > 0) {
                            Badge(containerColor = Primary) {
                                Text(pendingRequestsCount.toString(), color = Color.White)
                            }
                        }
                    }
                ) {
                    Icon(
                        imageVector = if (selectedTab == "network") Icons.Filled.Hub else Icons.Outlined.Hub,
                        contentDescription = "Network"
                    )
                }
            },
            label = { Text("Network", fontSize = 11.sp) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Primary,
                selectedTextColor = Primary,
                indicatorColor = SecondaryContainer
            ),
            modifier = Modifier.testTag("nav_tab_network")
        )

        NavigationBarItem(
            selected = selectedTab == "alerts",
            onClick = { onTabSelected("alerts") },
            icon = {
                BadgedBox(
                    badge = {
                        if (unreadNotificationsCount > 0) {
                            Badge(containerColor = Error) {
                                Text(unreadNotificationsCount.toString(), color = Color.White)
                            }
                        }
                    }
                ) {
                    Icon(
                        imageVector = if (selectedTab == "alerts") Icons.Filled.Notifications else Icons.Outlined.Notifications,
                        contentDescription = "Alerts"
                    )
                }
            },
            label = { Text("Alerts", fontSize = 11.sp) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Primary,
                selectedTextColor = Primary,
                indicatorColor = SecondaryContainer
            ),
            modifier = Modifier.testTag("nav_tab_alerts")
        )

        NavigationBarItem(
            selected = selectedTab == "profile",
            onClick = { onTabSelected("profile") },
            icon = {
                Icon(
                    imageVector = if (selectedTab == "profile") Icons.Filled.AccountCircle else Icons.Outlined.AccountCircle,
                    contentDescription = "Profile"
                )
            },
            label = { Text("Pass", fontSize = 11.sp) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Primary,
                selectedTextColor = Primary,
                indicatorColor = SecondaryContainer
            ),
            modifier = Modifier.testTag("nav_tab_profile")
        )
    }
}
