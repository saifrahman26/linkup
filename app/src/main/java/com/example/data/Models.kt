package com.example.data

data class Attendee(
    val id: String,
    val name: String,
    val title: String,
    val company: String,
    val location: String,
    val avatarUrl: String,
    val verified: Boolean = true,
    val badge: String = "YOU NEED ↔ THEY OFFER",
    val badgeType: BadgeType = BadgeType.MUTUAL_NEED,
    val building: String,
    val lookingFor: String,
    val canHelpWith: List<String>,
    val whyMeetReason: String,
    val status: String = "Available in Lounge",
    val isAvailable: Boolean = true,
    val recentTalk: String = "",
    val mutualContext: String = "Both checked in at Stage A · Keynote Session",
    val matchScore: Int = 94,
    val category: String = "Cofounders",
    val isSaved: Boolean = false,
    val connectionStatus: ConnectionStatus = ConnectionStatus.NOT_CONNECTED
)

enum class BadgeType {
    MUTUAL_NEED,
    COMPLEMENTARY,
    SHARED_FOCUS
}

enum class ConnectionStatus {
    NOT_CONNECTED,
    REQUESTED,
    CONNECTED,
    DECLINED
}

data class PendingIntro(
    val id: String,
    val attendee: Attendee,
    val requestedTimeAgo: String,
    val mutualTag: String,
    val personalNote: String? = null,
    val offerTag: String? = null
)

data class NotificationItem(
    val id: String,
    val title: String,
    val subtitle: String,
    val timeAgo: String,
    val category: String, // "requests", "matches", "system"
    val avatarUrl: String? = null,
    val isUnread: Boolean = true,
    val quote: String? = null,
    val actionType: NotificationAction = NotificationAction.VIEW_PROFILE
)

enum class NotificationAction {
    REPLY,
    VIEW_RECOMMENDATIONS,
    ACCEPT_DECLINE,
    VIEW_PROFILE
}

data class DomainInterest(
    val id: String,
    val name: String,
    val category: String, // "tech", "venture", "design"
    val isSelected: Boolean = false
)

data class IntentAnchor(
    val id: String,
    val title: String,
    val description: String,
    val iconName: String,
    val isSelected: Boolean = false
)

data class OfferingItem(
    val id: String,
    val title: String,
    val description: String,
    val iconName: String,
    val count: Int,
    val isSelected: Boolean = false
)

data class FunnelStage(
    val step: String,
    val name: String,
    val count: String,
    val subtitle: String,
    val retention: String,
    val dropPercent: String? = null,
    val progress: Float
)

data class Hotspot(
    val name: String,
    val subtitle: String,
    val activeLinks: Int,
    val tag: String? = null
)

data class LiveMatchPair(
    val personA: String,
    val roleA: String,
    val avatarA: String,
    val personB: String,
    val roleB: String,
    val avatarB: String,
    val affinity: String,
    val topic: String,
    val location: String,
    val timeAgo: String
)
