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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.OrbitHeader
import com.example.ui.theme.*

@Composable
fun EventQrEntryScreen(
    onScanOrContinue: () -> Unit,
    onOpenOrganizerTvMode: () -> Unit,
    onOrganizerDashboard: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            OrbitHeader(
                title = "EVENT ACCESS",
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
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            // Live Event Header Badge
            item {
                Surface(
                    color = SurfaceContainerLowest,
                    shape = RoundedCornerShape(12.dp),
                    shadowElevation = 1.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
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
                                text = "TECH SUMMIT '26 · ENTRY POINT",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF006948),
                                letterSpacing = 0.5.sp
                            )
                        }
                        Surface(
                            color = SecondaryContainer,
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = "Moscone West",
                                fontSize = 11.sp,
                                color = Primary,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }
                    }
                }
            }

            // Main Organizer Entry Message
            item {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Connect with the right people at this event",
                        fontSize = 24.sp,
                        lineHeight = 30.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = OnSurface,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Scan to find your best matches",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium,
                        color = Secondary,
                        textAlign = TextAlign.Center
                    )
                }
            }

            // Large QR Code Card
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(20.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, OutlineVariant, RoundedCornerShape(20.dp))
                        .clickable { onScanOrContinue() }
                        .testTag("event_qr_card")
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        // QR Graphic
                        Box(
                            modifier = Modifier
                                .size(210.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(Color(0xFFF9FAFA))
                                .border(1.5.dp, PrimaryFixed, RoundedCornerShape(16.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.QrCode2,
                                contentDescription = "Large QR Code",
                                tint = Color(0xFF151D21),
                                modifier = Modifier.size(190.dp)
                            )
                        }

                        // Takes less than 60 seconds
                        Surface(
                            color = PrimaryFixed,
                            shape = RoundedCornerShape(20.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Timer,
                                    contentDescription = null,
                                    tint = Primary,
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = "Takes less than 60 seconds",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Primary
                                )
                            }
                        }

                        // Social proof counter
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
                                text = "342 people are already networking",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = OnSurface
                            )
                        }
                    }
                }
            }

            // Attendee Action Button (Scan / Join)
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        onClick = onScanOrContinue,
                        colors = ButtonDefaults.buttonColors(containerColor = Primary),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .testTag("scan_enter_event_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.QrCodeScanner,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Scan QR / Tap to Enter",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    OutlinedButton(
                        onClick = onOpenOrganizerTvMode,
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = OnSurface),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(46.dp)
                            .testTag("open_projector_mode_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Tv,
                            contentDescription = null,
                            tint = Secondary,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Organizer Projector / TV Mode",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

            // Relationship-first note
            item {
                Surface(
                    color = SurfaceContainerLowest,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Psychology,
                            contentDescription = null,
                            tint = Primary,
                            modifier = Modifier.size(22.dp)
                        )
                        Column {
                            Text(
                                text = "Relationship-First Matching",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = OnSurface
                            )
                            Text(
                                text = "No blind scanning. You receive a curated 5-person networking plan with high-signal reasons to meet.",
                                fontSize = 11.sp,
                                color = Secondary,
                                lineHeight = 15.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
