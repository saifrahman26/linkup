package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.data.Attendee
import com.example.data.OrbitRepository
import com.example.ui.components.BottomNavigationTabs
import com.example.ui.components.OrbitHeader
import com.example.ui.screens.*
import com.example.ui.theme.OrbitTheme
import kotlinx.coroutines.launch

sealed class Screen {
    data object Welcome : Screen()
    data object Auth : Screen()
    data object OnboardingInterests : Screen()
    data object OnboardingIntents : Screen()
    data object OnboardingOffers : Screen()
    data class Main(val tab: String = "discover") : Screen()
    data class AttendeeDetail(val attendee: Attendee, val returnTab: String = "discover") : Screen()
    data class OrganizerDashboard(val returnScreen: Screen = Main()) : Screen()
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OrbitTheme {
                OrbitApp()
            }
        }
    }
}

@Composable
fun OrbitApp() {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Welcome) }
    var connectionDialogAttendee by remember { mutableStateOf<Attendee?>(null) }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val pendingIntros by OrbitRepository.pendingIntros.collectAsState()
    val notifications by OrbitRepository.notifications.collectAsState()
    val unreadNotifsCount = notifications.count { it.isUnread }

    // Handle system back button properly across screens
    BackHandler(enabled = currentScreen !is Screen.Welcome) {
        when (val screen = currentScreen) {
            is Screen.Auth -> currentScreen = Screen.Welcome
            is Screen.OnboardingInterests -> currentScreen = Screen.Auth
            is Screen.OnboardingIntents -> currentScreen = Screen.OnboardingInterests
            is Screen.OnboardingOffers -> currentScreen = Screen.OnboardingIntents
            is Screen.AttendeeDetail -> currentScreen = Screen.Main(screen.returnTab)
            is Screen.OrganizerDashboard -> currentScreen = screen.returnScreen
            is Screen.Main -> {
                if (screen.tab != "discover") {
                    currentScreen = Screen.Main("discover")
                } else {
                    currentScreen = Screen.Welcome
                }
            }
            Screen.Welcome -> {}
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when (val screen = currentScreen) {
            is Screen.Welcome -> {
                WelcomeScreen(
                    onStartOnboarding = { currentScreen = Screen.OnboardingInterests },
                    onSignIn = { currentScreen = Screen.Auth },
                    onOrganizerDashboard = { currentScreen = Screen.OrganizerDashboard(Screen.Welcome) }
                )
            }

            is Screen.Auth -> {
                AuthScreen(
                    onAuthenticated = { currentScreen = Screen.OnboardingInterests },
                    onBack = { currentScreen = Screen.Welcome },
                    onOrganizerDashboard = { currentScreen = Screen.OrganizerDashboard(Screen.Auth) }
                )
            }

            is Screen.OnboardingInterests -> {
                OnboardingInterestsScreen(
                    onContinue = { currentScreen = Screen.OnboardingIntents },
                    onSkip = { currentScreen = Screen.Main("discover") },
                    onBack = { currentScreen = Screen.Auth }
                )
            }

            is Screen.OnboardingIntents -> {
                OnboardingIntentsScreen(
                    onContinue = { currentScreen = Screen.OnboardingOffers },
                    onSkip = { currentScreen = Screen.Main("discover") },
                    onBack = { currentScreen = Screen.OnboardingInterests }
                )
            }

            is Screen.OnboardingOffers -> {
                OnboardingOffersScreen(
                    onFinish = { currentScreen = Screen.Main("discover") },
                    onBack = { currentScreen = Screen.OnboardingIntents }
                )
            }

            is Screen.Main -> {
                Scaffold(
                    topBar = {
                        OrbitHeader(
                            title = "ORBIT RADAR",
                            showBack = false,
                            onOrganizerClick = {
                                currentScreen = Screen.OrganizerDashboard(Screen.Main(screen.tab))
                            }
                        )
                    },
                    bottomBar = {
                        BottomNavigationTabs(
                            selectedTab = screen.tab,
                            onTabSelected = { newTab ->
                                currentScreen = Screen.Main(newTab)
                            },
                            pendingRequestsCount = pendingIntros.size,
                            unreadNotificationsCount = unreadNotifsCount
                        )
                    },
                    snackbarHost = { SnackbarHost(snackbarHostState) }
                ) { innerPadding ->
                    when (screen.tab) {
                        "discover" -> {
                            DiscoverScreen(
                                onAttendeeClick = { attendee ->
                                    currentScreen = Screen.AttendeeDetail(attendee, returnTab = "discover")
                                },
                                onConnectClick = { attendee ->
                                    connectionDialogAttendee = attendee
                                },
                                onOrganizerClick = {
                                    currentScreen = Screen.OrganizerDashboard(Screen.Main("discover"))
                                },
                                modifier = Modifier.padding(innerPadding)
                            )
                        }

                        "people" -> {
                            PeopleScreen(
                                onAttendeeClick = { attendee ->
                                    currentScreen = Screen.AttendeeDetail(attendee, returnTab = "people")
                                },
                                onConnectClick = { attendee ->
                                    connectionDialogAttendee = attendee
                                },
                                modifier = Modifier.padding(innerPadding)
                            )
                        }

                        "network" -> {
                            NetworkScreen(
                                onAttendeeClick = { attendee ->
                                    currentScreen = Screen.AttendeeDetail(attendee, returnTab = "network")
                                },
                                modifier = Modifier.padding(innerPadding)
                            )
                        }

                        "alerts" -> {
                            AlertsScreen(
                                onNavigateToNetwork = { currentScreen = Screen.Main("network") },
                                onNavigateToDiscover = { currentScreen = Screen.Main("discover") },
                                modifier = Modifier.padding(innerPadding)
                            )
                        }

                        "profile" -> {
                            ProfileScreen(
                                onSignOut = { currentScreen = Screen.Welcome },
                                onOrganizerDashboard = {
                                    currentScreen = Screen.OrganizerDashboard(Screen.Main("profile"))
                                },
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                    }
                }
            }

            is Screen.AttendeeDetail -> {
                AttendeeDetailScreen(
                    attendee = screen.attendee,
                    onBack = { currentScreen = Screen.Main(screen.returnTab) },
                    onSendConnectionRequest = {
                        connectionDialogAttendee = screen.attendee
                    }
                )
            }

            is Screen.OrganizerDashboard -> {
                OrganizerDashboardScreen(
                    onBack = { currentScreen = screen.returnScreen }
                )
            }
        }

        // Connection Request Dialog (Modal)
        connectionDialogAttendee?.let { attendee ->
            ConnectionRequestDialog(
                attendee = attendee,
                onDismiss = { connectionDialogAttendee = null },
                onSent = {
                    val name = attendee.name
                    connectionDialogAttendee = null
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(
                            message = "Connection request dispatched to $name! You'll be notified when accepted.",
                            duration = SnackbarDuration.Short
                        )
                    }
                }
            )
        }
    }
}
