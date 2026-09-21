package com.example.ui.screens

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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.data.Attendee
import com.example.data.ConnectionStatus
import com.example.data.OrbitRepository
import com.example.ui.components.StatusPill
import com.example.ui.theme.*

@Composable
fun PeopleScreen(
    onAttendeeClick: (Attendee) -> Unit,
    onConnectClick: (Attendee) -> Unit,
    modifier: Modifier = Modifier
) {
    val attendees by OrbitRepository.attendees.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    var selectedTag by remember { mutableStateOf("All") }
    val tags = listOf("All", "In Lounge", "Cofounders", "Infra", "Investors")

    val filteredAttendees = remember(attendees, searchQuery, selectedTag) {
        attendees.filter { attendee ->
            val matchesQuery = searchQuery.isEmpty() ||
                    attendee.name.contains(searchQuery, ignoreCase = true) ||
                    attendee.company.contains(searchQuery, ignoreCase = true) ||
                    attendee.title.contains(searchQuery, ignoreCase = true) ||
                    attendee.building.contains(searchQuery, ignoreCase = true)

            val matchesTag = when (selectedTag) {
                "In Lounge" -> attendee.status.contains("Lounge", ignoreCase = true)
                "Cofounders" -> attendee.category == "Cofounders"
                "Infra" -> attendee.category == "AI/Systems"
                "Investors" -> attendee.category == "Angel/Seed"
                else -> true
            }

            matchesQuery && matchesTag
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Search Bar
        item {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search by name, company, or tech stack...") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = Primary
                    )
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = "Clear")
                        }
                    }
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
                    .testTag("people_search_input")
            )
        }

        // Filter Tags Row
        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(tags) { tag ->
                    val isSelected = selectedTag == tag
                    FilterChip(
                        selected = isSelected,
                        onClick = { selectedTag = tag },
                        label = {
                            Text(
                                text = tag,
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

        // Count Summary
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${filteredAttendees.size} Attendees on Radar",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = Secondary
                )
                Text(
                    text = "Moscone West Live",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF006948)
                )
            }
        }

        // Attendee Directory List
        items(filteredAttendees, key = { it.id }) { attendee ->
            Card(
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
                shape = RoundedCornerShape(14.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onAttendeeClick(attendee) }
                    .testTag("person_item_${attendee.id}")
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                            .border(1.5.dp, PrimaryFixed, CircleShape)
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

                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = attendee.name,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = OnSurface
                            )
                            if (attendee.verified) {
                                Icon(
                                    imageVector = Icons.Default.Verified,
                                    contentDescription = "Verified",
                                    tint = Primary,
                                    modifier = Modifier.size(15.dp)
                                )
                            }
                        }

                        Text(
                            text = "${attendee.title} · ${attendee.company}",
                            fontSize = 12.sp,
                            color = Secondary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        Spacer(modifier = Modifier.height(4.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            StatusPill(text = attendee.status, isLive = attendee.isAvailable)
                            Surface(
                                color = SecondaryContainer,
                                shape = RoundedCornerShape(4.dp)
                            ) {
                                Text(
                                    text = "${attendee.matchScore}% FIT",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Primary,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }

                    if (attendee.connectionStatus == ConnectionStatus.CONNECTED) {
                        FilledTonalButton(
                            onClick = { onAttendeeClick(attendee) },
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                            colors = ButtonDefaults.filledTonalButtonColors(
                                containerColor = Color(0xFFE6F7ED),
                                contentColor = Color(0xFF006948)
                            ),
                            modifier = Modifier.height(34.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Chat,
                                contentDescription = null,
                                modifier = Modifier.size(14.dp)
                            )
                        }
                    } else {
                        Button(
                            onClick = { onConnectClick(attendee) },
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Primary),
                            modifier = Modifier.height(34.dp)
                        ) {
                            Text("Intro", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}
