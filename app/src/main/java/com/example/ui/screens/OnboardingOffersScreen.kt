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
import com.example.ui.components.OrbitHeader
import com.example.ui.theme.*

@Composable
fun OnboardingOffersScreen(
    onFinish: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val offerings by OrbitRepository.offerings.collectAsState()
    val selectedCount = offerings.count { it.isSelected }

    Scaffold(
        topBar = {
            Column {
                OrbitHeader(
                    title = "STEP 3 OF 3",
                    showBack = true,
                    onBack = onBack,
                    showOrganizerBadge = false
                )
                LinearProgressIndicator(
                    progress = { 1.0f },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp),
                    color = Primary,
                    trackColor = SurfaceContainerHigh
                )
            }
        },
        bottomBar = {
            Surface(
                color = SurfaceContainerLowest,
                shadowElevation = 8.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 12.dp)
                ) {
                    Button(
                        onClick = onFinish,
                        colors = ButtonDefaults.buttonColors(containerColor = Primary),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .testTag("offers_finish_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Bolt,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Launch My Orbit Radar",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
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
                .padding(horizontal = 20.dp),
            contentPadding = PaddingValues(top = 16.dp, bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Retained context pill
            item {
                Surface(
                    color = SurfaceContainerLow,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = Color(0xFF00A86B),
                            modifier = Modifier.size(14.dp)
                        )
                        Text(
                            text = "Looking for: Cofounder, Customers, Senior Talent",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF006948)
                        )
                    }
                }
            }

            // Headline
            item {
                Column {
                    Text(
                        text = "What can you help others with?",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = OnSurface,
                        lineHeight = 32.sp
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Networking is reciprocal. Select areas where you can provide authentic insight, feedback, or introductions.",
                        fontSize = 13.sp,
                        color = Secondary,
                        lineHeight = 18.sp
                    )
                }
            }

            // Synergy Callout
            item {
                Surface(
                    color = Color(0xFFE6F7ED),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.SwapCalls,
                            contentDescription = null,
                            tint = Color(0xFF00A86B),
                            modifier = Modifier.size(20.dp)
                        )
                        Column {
                            Text(
                                text = "High-Signal Synergy",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF006948)
                            )
                            Text(
                                text = "You have 42 attendees actively seeking your areas of expertise in Lounge Level 2 right now.",
                                fontSize = 11.sp,
                                color = Color(0xFF004F35),
                                lineHeight = 15.sp
                            )
                        }
                    }
                }
            }

            // Offerings Items
            items(offerings.size) { index ->
                val offering = offerings[index]
                OfferingCard(
                    offering = offering,
                    onClick = { OrbitRepository.toggleOffering(offering.id) }
                )
            }

            // Complementary Peers Preview
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = "Complementary matches ready for you",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = OnSurface
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            listOf(
                                "https://lh3.googleusercontent.com/aida-public/AB6AXuCFgQ2tPJcDMmZWVAJkmUsQFsZhXcqHT8rLMMpOFvbwdlMxNP4klXPg6rENE3ycUrRXGj5V7eGX4Y_CE33VgxBfQBwAHoD6Ktn6hERZvFZiu88nhcK1M5FSGh3Mj-DC3Zd5l9v-T0mVXIwl7ZhSkZQlpNO9ZwD8JNWT1ZashwLDmhJOXQVEBB6wRwshvTcFMVqNYJ6gAFFfI0bkx6mo-3v3cVxtl8MY0CqXks7alX-UgdlJ6O5YT3GkwQ",
                                "https://lh3.googleusercontent.com/aida-public/AB6AXuCiDvnOm1WSSbErhYpRDaXr6cL5oE6rkNga9zk3jqGU2nSFSRjK3rddT08mB2jw7O5mXEkQhdEZokwpYZF6x1qAcBFWZqA_jmQSdwOWOzGNOwHI1uKwziL0A8Ef7dQSU9zOxaU62y2COhddePij7kRn7IAoscWQQN23QT3rmYuP__vkn_w_vk8G80A_zR6kpvAlgDHZJTdyXNPa_UANWNAbY-OlSxeHApbGeVRp_6Xo5OWyR_RKkNRflg"
                            ).forEach { avatarUrl ->
                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clip(CircleShape)
                                        .border(1.5.dp, Primary, CircleShape)
                                ) {
                                    AsyncImage(
                                        model = ImageRequest.Builder(LocalContext.current)
                                            .data(avatarUrl)
                                            .crossfade(true)
                                            .build(),
                                        contentDescription = null,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }
                            }
                            Text(
                                text = "Aisha Khan & Marcus Chen are waiting in your radar.",
                                fontSize = 11.sp,
                                color = Secondary
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun OfferingCard(
    offering: com.example.data.OfferingItem,
    onClick: () -> Unit
) {
    Surface(
        color = SurfaceContainerLowest,
        shape = RoundedCornerShape(12.dp),
        border = if (offering.isSelected) {
            androidx.compose.foundation.BorderStroke(2.dp, Primary)
        } else {
            androidx.compose.foundation.BorderStroke(1.dp, OutlineVariant)
        },
        shadowElevation = if (offering.isSelected) 2.dp else 0.dp,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .testTag("offering_card_${offering.id}")
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(if (offering.isSelected) PrimaryContainer else SurfaceContainerHigh),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = if (offering.isSelected) Color.White else Primary,
                    modifier = Modifier.size(20.dp)
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = offering.title,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = OnSurface
                    )
                    Surface(
                        color = SecondaryContainer,
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            text = "${offering.count} seeking",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Primary,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
                Text(
                    text = offering.description,
                    fontSize = 12.sp,
                    color = Secondary,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }

            Checkbox(
                checked = offering.isSelected,
                onCheckedChange = { onClick() },
                colors = CheckboxDefaults.colors(
                    checkedColor = Primary,
                    checkmarkColor = Color.White
                )
            )
        }
    }
}
