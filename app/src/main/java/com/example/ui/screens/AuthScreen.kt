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
fun AuthScreen(
    onAuthenticated: () -> Unit,
    onBack: () -> Unit,
    onOrganizerDashboard: () -> Unit,
    modifier: Modifier = Modifier
) {
    var email by remember { mutableStateOf("elena@vesper-ai.com") }
    var showMapModal by remember { mutableStateOf(false) }

    val socialProofAvatars = listOf(
        "https://lh3.googleusercontent.com/aida-public/AB6AXuCXoYVrWuPaTQJlnuLTMT4p3P1PKdw1K7CRj7H78bve-Cgdwi-K7WTqfyDUDDJVMVuQflR27KkmVJ5OdX-iJ3vZU1eNIJlM9iet37knhCKPD3Vzkh9K34hYHt2etl4D8hSh1QW--0n8o8_Xv_EmUcydvuNsmYdEPl-jlM96-Ekpb88AVemvLsFkoFAQpvDjAoWVpNOgv5-mfWUsHOmRkAFJNkkUPFiwPlO6r-h7AKe7xC0786TS_fvKBA",
        "https://lh3.googleusercontent.com/aida-public/AB6AXuDHlcadYbVBfTu1YhhaHvx1HNvVPl-W2BxDO9sS6NJ6CZZ2yDSy2t7f03hP1GnHl16oF15BdbUCdX7aHSu9lxLX6j3aPWw7gH2niuCqj710e2hyt2GTfCKPhp4kfozzVNgoJNg7X1ISVOisGHUr5r5d9HMoPYcA_HqwNUGAwXrSX-ypyuXT409WgHvXiVjT4Y5LijppLSX9LqUsVCYj2o-duiUc2c5xZ2ruR27s0NLVmQQkVlPSiSs29Q",
        "https://lh3.googleusercontent.com/aida-public/AB6AXuBH9W7Pfmt7aM-YUreNfieU4W0wip6MizdlFoVbag7561X-NG6hsuCpPxD9yCBunJYp3hvaDNxoz2JLUVd1ZEu-YIXh5tVN4DySEX22V7Q2S_a3kYPkB6Y8XyFVJDNS6pjoPygenEjOh-0G9TJt0c7A-2wNqXIMb6QvooA4wHD1xu9EdNT2xV9gEBE35J1qUZT07vkq1rU-WtJ899CdtERqQT4cNsgOEx9xxJpfdmBQ-KqkcW1cxYwVWg"
    )

    Scaffold(
        topBar = {
            OrbitHeader(
                title = "AUTHENTICATION",
                showBack = true,
                onBack = onBack,
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
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            // Pass Verified Status Badge
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        color = Color(0xFFE6F7ED),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Verified,
                                contentDescription = null,
                                tint = Color(0xFF00A86B),
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = "Tech Summit 2026 · Delegate Pass",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF006948)
                            )
                        }
                    }

                    Text(
                        text = "Step 1 of 4",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Secondary
                    )
                }
            }

            // Title & Subtitle
            item {
                Column {
                    Text(
                        text = "Welcome to Tech Summit '26",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = OnSurface,
                        lineHeight = 34.sp
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Let's build your networking plan.",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Primary,
                        lineHeight = 22.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Takes less than 60 seconds to personalize 5 high-signal people worth meeting today.",
                        fontSize = 13.sp,
                        color = Secondary,
                        lineHeight = 18.sp
                    )
                }
            }

            // Privacy & Signal-First Card
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
                            imageVector = Icons.Default.Shield,
                            contentDescription = null,
                            tint = Primary,
                            modifier = Modifier.size(22.dp)
                        )
                        Column {
                            Text(
                                text = "High Signal, Zero Spam",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = OnSurface
                            )
                            Text(
                                text = "Your contact info is never shared publicly. Only mutual in-person connections can exchange direct passes.",
                                fontSize = 11.sp,
                                color = Secondary,
                                lineHeight = 15.sp
                            )
                        }
                    }
                }
            }

            // Single Sign-On Buttons
            item {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    // Google SSO
                    OutlinedButton(
                        onClick = onAuthenticated,
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = SurfaceContainerLowest,
                            contentColor = OnSurface
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .testTag("auth_google_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.GTranslate,
                            contentDescription = "Google",
                            tint = Primary,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Continue with Google",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    // LinkedIn SSO
                    OutlinedButton(
                        onClick = onAuthenticated,
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = SurfaceContainerLowest,
                            contentColor = OnSurface
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .testTag("auth_linkedin_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "LinkedIn",
                            tint = Color(0xFF0A66C2),
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Continue with LinkedIn",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

            // Divider
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    HorizontalDivider(modifier = Modifier.weight(1f), color = OutlineVariant)
                    Text(
                        text = "OR WORK EMAIL",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Secondary,
                        modifier = Modifier.padding(horizontal = 12.dp),
                        letterSpacing = 0.5.sp
                    )
                    HorizontalDivider(modifier = Modifier.weight(1f), color = OutlineVariant)
                }
            }

            // Work Email Field & Continue
            item {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Event Registration Email") },
                        placeholder = { Text("e.g. name@company.com") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.MailOutline,
                                contentDescription = null,
                                tint = Primary
                            )
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Primary,
                            unfocusedBorderColor = OutlineVariant,
                            focusedContainerColor = SurfaceContainerLowest,
                            unfocusedContainerColor = SurfaceContainerLowest
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("auth_email_input")
                    )

                    Button(
                        onClick = onAuthenticated,
                        colors = ButtonDefaults.buttonColors(containerColor = Primary),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .testTag("auth_continue_button")
                    ) {
                        Text(
                            text = "Continue to Intent Setup",
                            fontSize = 15.sp,
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
                }
            }

            // Verified Attendee Social Proof Avatars
            item {
                Surface(
                    color = SurfaceContainerLowest,
                    shape = RoundedCornerShape(12.dp),
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
                            socialProofAvatars.forEach { url ->
                                Box(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clip(CircleShape)
                                        .border(2.dp, SurfaceContainerLowest, CircleShape)
                                ) {
                                    AsyncImage(
                                        model = ImageRequest.Builder(LocalContext.current)
                                            .data(url)
                                            .crossfade(true)
                                            .build(),
                                        contentDescription = null,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }
                            }
                        }

                        Text(
                            text = "1,480+ peers checked in today · Active",
                            fontSize = 12.sp,
                            color = OnSurfaceVariant,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            // Help Concierge Desk
            item {
                Surface(
                    color = SurfaceContainer,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(
                                imageVector = Icons.Default.SupportAgent,
                                contentDescription = null,
                                tint = Secondary,
                                modifier = Modifier.size(20.dp)
                            )
                            Column {
                                Text(
                                    text = "Orbit Help Desk",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = OnSurface
                                )
                                Text(
                                    text = "Stage A Entrance · Moscone West",
                                    fontSize = 11.sp,
                                    color = Secondary
                                )
                            }
                        }

                        TextButton(
                            onClick = { showMapModal = true },
                            modifier = Modifier.testTag("auth_view_map_button")
                        ) {
                            Text(
                                text = "View Map",
                                fontSize = 12.sp,
                                color = Primary,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }

    if (showMapModal) {
        AlertDialog(
            onDismissRequest = { showMapModal = false },
            title = {
                Text(
                    text = "Moscone West Venue Map",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = "Orbit Lounge is located on Level 2, directly next to Stage A and the Developer Terrace.",
                        fontSize = 13.sp,
                        color = OnSurfaceVariant
                    )
                    Card(
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data("https://lh3.googleusercontent.com/aida-public/AB6AXuDwV5MXQEHSnB4vk76Sr1uztG2bnUuHSbQ0_GytXbtFPcz6j_5yhJShSczHjSzK1sGf910ezdrClMxULsNPp2xThyq1yCquGTZ9zzGFQatL2OgOpBdAkSWMTSYP4GuRiZwIV04es7a1ExYEkpvJh8MKKqFu8wfBQZPtMBRukkGgD9BT62FvTbai872psHa1R6fGL43d7T4GNyobvQAzk3dkd_dEG9Rrw1HPsEQ5kQOKlEVxQ1GZNMUEmQ")
                                .crossfade(true)
                                .build(),
                            contentDescription = "Moscone West Floor Map",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = { showMapModal = false },
                    colors = ButtonDefaults.buttonColors(containerColor = Primary)
                ) {
                    Text("Got it")
                }
            }
        )
    }
}
