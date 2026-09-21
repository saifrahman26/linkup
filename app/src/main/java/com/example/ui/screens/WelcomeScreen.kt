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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.data.OrbitRepository
import com.example.ui.components.OrbitHeader
import com.example.ui.theme.*

@Composable
fun WelcomeScreen(
    onStartOnboarding: () -> Unit,
    onSignIn: () -> Unit,
    onOrganizerDashboard: () -> Unit,
    modifier: Modifier = Modifier
) {
    val sampleAvatars = listOf(
        "https://lh3.googleusercontent.com/aida-public/AB6AXuCFgQ2tPJcDMmZWVAJkmUsQFsZhXcqHT8rLMMpOFvbwdlMxNP4klXPg6rENE3ycUrRXGj5V7eGX4Y_CE33VgxBfQBwAHoD6Ktn6hERZvFZiu88nhcK1M5FSGh3Mj-DC3Zd5l9v-T0mVXIwl7ZhSkZQlpNO9ZwD8JNWT1ZashwLDmhJOXQVEBB6wRwshvTcFMVqNYJ6gAFFfI0bkx6mo-3v3cVxtl8MY0CqXks7alX-UgdlJ6O5YT3GkwQ",
        "https://lh3.googleusercontent.com/aida-public/AB6AXuA53-QVUy-lydHRLIYcqhnEpDKS6SNihTtM2TYKmHen23nJP7Yy7lohMoAV1flUzSQKkMkmvFPAAYo6EEjPGagXKuFhAoJ1vufYZ0BQ94QxonVrnnRAwnCbjG6PXNsRG2aBznyqvXhYERZ_cFaTJTMLMtC7JXtQC07QwAD0GQcghAQrohT_4v6xK37u2WZ55IEUL9Tmp3vkiI5zlrung0-LzuruJN2rnnIk9Q3DCEhYWE8p-oHBpD4kzw",
        "https://lh3.googleusercontent.com/aida-public/AB6AXuCiDvnOm1WSSbErhYpRDaXr6cL5oE6rkNga9zk3jqGU2nSFSRjK3rddT08mB2jw7O5mXEkQhdEZokwpYZF6x1qAcBFWZqA_jmQSdwOWOzGNOwHI1uKwziL0A8Ef7dQSU9zOxaU62y2COhddePij7kRn7IAoscWQQN23QT3rmYuP__vkn_w_vk8G80A_zR6kpvAlgDHZJTdyXNPa_UANWNAbY-OlSxeHApbGeVRp_6Xo5OWyR_RKkNRflg",
        "https://lh3.googleusercontent.com/aida-public/AB6AXuC5OeyMEslPGT6LN2TaGyMNbSxK1e7ecfLtrisrlVLePYOnVMA9wM5skYgPkPl2Fr1lCnS-bbGNDdIoT3ftwssXzdsvhRUptDG0PtJPkr0Pcy7_mAnHyuVosaa2ZTQBqZ0epdJpdXGGC3YU2xPvTXP7qGfnCm-jV0O2i1njykmbIOFdLppu4p7RwD7FV7i6jFRPstuclZzPWLYV2LuzoyjxH584CvLN0OUvaIVF4ovRnz1STG456n-pog"
    )

    Scaffold(
        topBar = {
            OrbitHeader(
                title = "TECHSUMMIT '26",
                showOrganizerBadge = true,
                onOrganizerClick = onOrganizerDashboard
            )
        },
        containerColor = Surface
    ) { innerPadding ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp),
            contentPadding = PaddingValues(top = 16.dp, bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Live Status Card
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
                                    text = "LIVE NOW · 1,420 CHECKED IN",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF006948),
                                    letterSpacing = 0.5.sp
                                )
                            }
                            Surface(
                                color = SurfaceContainerHigh,
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Text(
                                    text = "Moscone West · SF",
                                    fontSize = 11.sp,
                                    color = Secondary,
                                    fontWeight = FontWeight.Medium,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "Tech Summit 2026",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = OnSurface
                        )
                        Text(
                            text = "The premier gathering for foundation models, autonomous agents, and next-gen cloud systems.",
                            fontSize = 13.sp,
                            color = OnSurfaceVariant,
                            lineHeight = 18.sp,
                            modifier = Modifier.padding(top = 4.dp)
                        )

                        Spacer(modifier = Modifier.height(12.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            listOf("#AI-Infra", "#Enterprise-SaaS", "#Series-A").forEach { tag ->
                                Surface(
                                    color = SecondaryContainer,
                                    shape = RoundedCornerShape(6.dp)
                                ) {
                                    Text(
                                        text = tag,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = Primary,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Headline & Core Value Proposition
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Surface(
                        color = PrimaryFixed,
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier.padding(bottom = 12.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.FlashOn,
                                contentDescription = null,
                                tint = Primary,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Instant Match Engine",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Primary
                            )
                        }
                    }

                    Text(
                        text = "Find the people you\nshould meet today.",
                        fontSize = 30.sp,
                        lineHeight = 36.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = OnSurface,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Zero awkward hallway small talk. Orbit matches your immediate needs with active attendees in the Lounge.",
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        color = Secondary,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                }
            }

            // Trust & Verified Attendees Proof
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy((-8).dp)
                        ) {
                            sampleAvatars.forEach { avatarUrl ->
                                Box(
                                    modifier = Modifier
                                        .size(34.dp)
                                        .clip(CircleShape)
                                        .border(2.dp, SurfaceContainerLow, CircleShape)
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
                        }

                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = "380+ Founders & Leads",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = OnSurface
                            )
                            Text(
                                text = "Available in Lounge 2",
                                fontSize = 11.sp,
                                color = Color(0xFF006948),
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }

            // Key Pillars
            item {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    PillarItem(
                        icon = Icons.Default.Tune,
                        title = "Zero 20-Minute Forms",
                        description = "Pick your intent in 3 taps: Cofounder, Customers, Capital, or Talent."
                    )
                    PillarItem(
                        icon = Icons.Default.Psychology,
                        title = "Need-Offer Synergy",
                        description = "See high-signal rationale for why two attendees should talk right now."
                    )
                    PillarItem(
                        icon = Icons.Default.Place,
                        title = "In-Person Orbit Lounge",
                        description = "Direct introductions pinned to Moscone West tables with 45m timebox."
                    )
                }
            }

            // Live Match Simulation Strip
            item {
                Surface(
                    color = SurfaceContainerHighest,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Hub,
                            contentDescription = null,
                            tint = Primary,
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = "Just matched: Series A CTO ↔ GPU Infra Architect · 4s ago",
                            fontSize = 11.sp,
                            color = OnSurfaceVariant,
                            fontWeight = FontWeight.Medium,
                            maxLines = 1
                        )
                    }
                }
            }

            // Primary Call to Actions
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        onClick = onStartOnboarding,
                        colors = ButtonDefaults.buttonColors(containerColor = Primary),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .testTag("welcome_get_started_button")
                    ) {
                        Text(
                            text = "Find My People",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    OutlinedButton(
                        onClick = onSignIn,
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = OnSurface),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("welcome_sign_in_button")
                    ) {
                        Text(
                            text = "Already registered? Sign In",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    TextButton(
                        onClick = onOrganizerDashboard,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("welcome_organizer_link")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Analytics,
                            contentDescription = null,
                            tint = Secondary,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "View Live Event Organizer Dashboard →",
                            fontSize = 12.sp,
                            color = Secondary,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PillarItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    description: String
) {
    Surface(
        color = SurfaceContainerLowest,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(PrimaryFixed),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Primary,
                    modifier = Modifier.size(20.dp)
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = OnSurface
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = description,
                    fontSize = 12.sp,
                    lineHeight = 16.sp,
                    color = Secondary
                )
            }
        }
    }
}
