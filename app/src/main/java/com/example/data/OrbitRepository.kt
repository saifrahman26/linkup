package com.example.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

object OrbitRepository {

    // Current logged-in attendee profile
    val currentUser = Attendee(
        id = "user_me",
        name = "Elena Vance",
        title = "Founder & CEO",
        company = "Vesper AI",
        location = "San Francisco, CA",
        avatarUrl = "https://lh3.googleusercontent.com/aida/AEtjO1VGz9UpI1h0zf7T_H62cSBOd9fEyEuCCCUXctjGnjQTLf95xoi6Pr8TQhPuU6nt3p4GfptZlGBPXYNtlA9ZnVoBvoMXLHS6w_-z1ZES7Djire2D3wYn8W7Pm9GisqI-tusj_dQEf7dxM-hddTgBOkc2-uY6OimJZ-TShJYUVqrfxXcvLtDvqEcmWEej2Zr01LVrZ2DlhM4P4ibqHA3HV6aRbWduls5-hvsaqXVRTSpdtdxVuUrhBA6e8SSJ",
        building = "Autonomous agents for enterprise customer support pipelines with verifiable grounding.",
        lookingFor = "Seed investors, early enterprise pilot customers, distributed systems lead engineer.",
        canHelpWith = listOf("AI / ML Architecture", "Early GTM", "Fundraising Strategy"),
        whyMeetReason = "Tech Summit 2026 Delegate #8491 · Pass verified",
        status = "Orbit Lounge Level 2 (LIVE)"
    )

    private val initialAttendees = listOf(
        Attendee(
            id = "att_1",
            name = "Aisha Khan",
            title = "VP of Infrastructure",
            company = "ScaleGrid Systems",
            location = "San Francisco, CA",
            avatarUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuCFgQ2tPJcDMmZWVAJkmUsQFsZhXcqHT8rLMMpOFvbwdlMxNP4klXPg6rENE3ycUrRXGj5V7eGX4Y_CE33VgxBfQBwAHoD6Ktn6hERZvFZiu88nhcK1M5FSGh3Mj-DC3Zd5l9v-T0mVXIwl7ZhSkZQlpNO9ZwD8JNWT1ZashwLDmhJOXQVEBB6wRwshvTcFMVqNYJ6gAFFfI0bkx6mo-3v3cVxtl8MY0CqXks7alX-UgdlJ6O5YT3GkwQ",
            badge = "YOU NEED ↔ THEY OFFER",
            badgeType = BadgeType.MUTUAL_NEED,
            building = "Fault-tolerant multi-cloud scheduler optimizing GPU clusters for large foundation model training.",
            lookingFor = "Early enterprise design partners, Series A co-investors, high-throughput network engineers.",
            canHelpWith = listOf("Distributed Systems", "GPU Infrastructure", "Cloud Architecture"),
            whyMeetReason = "You're looking for distributed systems expertise; Aisha runs infra for 12k+ GPUs and is actively sourcing early AI pilot customers.",
            status = "Orbit Lounge Level 2",
            isAvailable = true,
            recentTalk = "Speaker: Scaling Inference Pipelines (Room 302, 2:30 PM)",
            mutualContext = "Both checked in at Stage A · Keynote Session",
            matchScore = 96,
            category = "AI/Systems"
        ),
        Attendee(
            id = "att_2",
            name = "Elena Rostova",
            title = "Principal ML Architect",
            company = "Synthetix Labs",
            location = "Zurich & San Francisco",
            avatarUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuA53-QVUy-lydHRLIYcqhnEpDKS6SNihTtM2TYKmHen23nJP7Yy7lohMoAV1flUzSQKkMkmvFPAAYo6EEjPGagXKuFhAoJ1vufYZ0BQ94QxonVrnnRAwnCbjG6PXNsRG2aBznyqvXhYERZ_cFaTJTMLMtC7JXtQC07QwAD0GQcghAQrohT_4v6xK37u2WZ55IEUL9Tmp3vkiI5zlrung0-LzuruJN2rnnIk9Q3DCEhYWE8p-oHBpD4kzw",
            badge = "COMPLEMENTARY FOCUS",
            badgeType = BadgeType.COMPLEMENTARY,
            building = "Novel alignment algorithms reducing hallucination in domain-specific agentic loops.",
            lookingFor = "Founding team partners, enterprise customers in legal/compliance, angel check ($50k).",
            canHelpWith = listOf("Model Alignment", "Fine-Tuning", "Agent Architecture"),
            whyMeetReason = "Shared research interests in agent grounding. Elena previously led research at DeepMind and has 4 patents on verifiable reasoning.",
            status = "Demo Floor Stage Area",
            isAvailable = true,
            recentTalk = "Panelist: Beyond RAG: Grounded Agent Architectures",
            mutualContext = "Both marked 'AI & Foundation Models' as primary domain",
            matchScore = 94,
            category = "Cofounders"
        ),
        Attendee(
            id = "att_3",
            name = "Marcus Chen",
            title = "Founding Partner",
            company = "Horizon Horizon Capital",
            location = "Menlo Park, CA",
            avatarUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuCiDvnOm1WSSbErhYpRDaXr6cL5oE6rkNga9zk3jqGU2nSFSRjK3rddT08mB2jw7O5mXEkQhdEZokwpYZF6x1qAcBFWZqA_jmQSdwOWOzGNOwHI1uKwziL0A8Ef7dQSU9zOxaU62y2COhddePij7kRn7IAoscWQQN23QT3rmYuP__vkn_w_vk8G80A_zR6kpvAlgDHZJTdyXNPa_UANWNAbY-OlSxeHApbGeVRp_6Xo5OWyR_RKkNRflg",
            badge = "SHARED INTENT: ANGEL/SEED",
            badgeType = BadgeType.SHARED_FOCUS,
            building = "Deploying $45M Seed-to-Series-A fund concentrated entirely on vertical AI agents and dev tools.",
            lookingFor = "Pre-seed & Seed stage technical founders with proprietary agent evaluation benchmarks.",
            canHelpWith = listOf("Seed Fundraising", "Board Governance", "Follow-on Rounds"),
            whyMeetReason = "Actively writing $1M–$2.5M lead checks for AI applications with demonstrable moat; viewed your project profile earlier today.",
            status = "VIP Lounge & Terrace",
            isAvailable = true,
            recentTalk = "Keynote: What We Look For in Technical Founders (11:00 AM)",
            mutualContext = "Mutual connection with Sarah Lin (Connected 2h ago)",
            matchScore = 91,
            category = "Angel/Seed"
        ),
        Attendee(
            id = "att_4",
            name = "David Vane",
            title = "Head of Product",
            company = "Kestrel Data",
            location = "New York, NY",
            avatarUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuC5OeyMEslPGT6LN2TaGyMNbSxK1e7ecfLtrisrlVLePYOnVMA9wM5skYgPkPl2Fr1lCnS-bbGNDdIoT3ftwssXzdsvhRUptDG0PtJPkr0Pcy7_mAnHyuVosaa2ZTQBqZ0epdJpdXGGC3YU2xPvTXP7qGfnCm-jV0O2i1njykmbIOFdLppu4p7RwD7FV7i6jFRPstuclZzPWLYV2LuzoyjxH584CvLN0OUvaIVF4ovRnz1STG456n-pog",
            badge = "COMPLEMENTARY SKILLS",
            badgeType = BadgeType.COMPLEMENTARY,
            building = "Real-time streaming ETL engine connecting legacy data warehouses to vector databases.",
            lookingFor = "Founding ML engineer, AI application design partners, early customers.",
            canHelpWith = listOf("Enterprise GTM", "Product Strategy", "B2B Sales"),
            whyMeetReason = "David scaled Kestrel from 0 to $4M ARR in 18 months; seeking technical co-founder for a stealth robotics spinout.",
            status = "Hallway B",
            isAvailable = true,
            recentTalk = "Attending: Dev Infra Summit (Room 204)",
            mutualContext = "Both attended Enterprise Architecture Roundtable",
            matchScore = 88,
            category = "Cofounders"
        ),
        Attendee(
            id = "att_5",
            name = "Sarah Lin",
            title = "Staff Research Engineer",
            company = "Cerebral Compute",
            location = "Seattle, WA",
            avatarUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuCXA-CTNoF3D-UrtgM7EaMXSuns9LFrG0cVbTCtj12itUATAZdTTVEQ7HHHHF04ZNhQZeThOGOKxGZsSisikIdLqdpSZdsfLIGIencJTq6P1LCP_4FNnibvFXq2hG3D7IA2yZiHUGvS6uDzaPfLqaPJ0TJJBdxzAC2884mrx81HbW9zAanAFfei4V3b7NSoDrstUynBe0AgS-tEVZz7sEX-fUaSwIBSIGPsrtGEuCJ5InrPkGjqf1rp5g",
            badge = "MUTUAL INTEREST",
            badgeType = BadgeType.MUTUAL_NEED,
            building = "Sparse attention kernels and specialized inference engines for quantized on-device models.",
            lookingFor = "Application developers deploying edge models, research collaborators.",
            canHelpWith = listOf("Model Quantization", "CUDA Kernels", "Edge Inference"),
            whyMeetReason = "Connected 2 hours ago. Sarah offered to benchmark Vesper's latency requirements against custom kernels.",
            status = "Orbit Lounge Level 2",
            isAvailable = true,
            recentTalk = "Workshop: Writing Triton Kernels for Custom Attention",
            mutualContext = "Direct connection made at Tech Summit 2026",
            matchScore = 95,
            category = "AI/Systems",
            connectionStatus = ConnectionStatus.CONNECTED
        ),
        Attendee(
            id = "att_6",
            name = "Julian Croft",
            title = "Managing Director",
            company = "Atlas Venture Partners",
            location = "San Francisco, CA",
            avatarUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuBrauQMpkzIj3x4BSCBcOy0TdFtIeaq-Dfa-EELrWPAPymq9Cnz80v7Bm7Lns6BYiDO3zAaves5nYpUYwf_uGz2lGLuD7pH-KKlijClvIeY6OxMgGNiEH_dWga2qvxUhfUKGFF8DoOtBF8si_NW724_e6n6M7K-2AsBntfhtwhoxhmtAK_bl2YkJOcPdlq39fePtCx_eEa29PrpAtrb7rBxGI72tUA54IZqeLLJFM5AveMFbCxKt63F4w",
            badge = "CONNECTED",
            badgeType = BadgeType.MUTUAL_NEED,
            building = "Early-stage infrastructure and developer tooling fund with $120M under management.",
            lookingFor = "Exceptional founders building mission-critical enterprise AI systems.",
            canHelpWith = listOf("Fundraising", "Talent Recruitment", "GTM Acceleration"),
            whyMeetReason = "Met at Stage B earlier today. Julian invited Elena to Atlas AI Dinner tonight.",
            status = "Stage A Terrace",
            isAvailable = true,
            recentTalk = "Judge: Tech Summit 2026 Startup Showcase",
            mutualContext = "Connected 1h ago · Met at Stage B",
            matchScore = 92,
            category = "Angel/Seed",
            connectionStatus = ConnectionStatus.CONNECTED
        )
    )

    private val _attendees = MutableStateFlow(initialAttendees)
    val attendees: StateFlow<List<Attendee>> = _attendees.asStateFlow()

    private val initialPendingIntros = listOf(
        PendingIntro(
            id = "intro_1",
            attendee = initialAttendees[0], // Aisha Khan
            requestedTimeAgo = "12m ago",
            mutualTag = "High Signal Match · 96% Fit",
            personalNote = "Saw your project on agent grounding. I lead infra at ScaleGrid and would love to compare notes over coffee in the Lounge.",
            offerTag = "They offer: Distributed Systems"
        ),
        PendingIntro(
            id = "intro_2",
            attendee = initialAttendees[3], // David Vane
            requestedTimeAgo = "34m ago",
            mutualTag = "Both attended Enterprise Roundtable",
            personalNote = "Hey Elena! Love what you're doing at Vesper. We're looking at integrating agentic workflows into our ETL engine.",
            offerTag = "They offer: Enterprise GTM"
        )
    )

    private val _pendingIntros = MutableStateFlow(initialPendingIntros)
    val pendingIntros: StateFlow<List<PendingIntro>> = _pendingIntros.asStateFlow()

    private val initialNotifications = listOf(
        NotificationItem(
            id = "notif_1",
            title = "Aisha Khan wants to connect with you",
            subtitle = "VP of Infrastructure at ScaleGrid Systems · 96% Match",
            timeAgo = "12m ago",
            category = "requests",
            avatarUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuCFgQ2tPJcDMmZWVAJkmUsQFsZhXcqHT8rLMMpOFvbwdlMxNP4klXPg6rENE3ycUrRXGj5V7eGX4Y_CE33VgxBfQBwAHoD6Ktn6hERZvFZiu88nhcK1M5FSGh3Mj-DC3Zd5l9v-T0mVXIwl7ZhSkZQlpNO9ZwD8JNWT1ZashwLDmhJOXQVEBB6wRwshvTcFMVqNYJ6gAFFfI0bkx6mo-3v3cVxtl8MY0CqXks7alX-UgdlJ6O5YT3GkwQ",
            isUnread = true,
            quote = "\"Saw your project on agent grounding. I lead infra at ScaleGrid and would love to compare notes over coffee in the Lounge.\"",
            actionType = NotificationAction.ACCEPT_DECLINE
        ),
        NotificationItem(
            id = "notif_2",
            title = "David Vane accepted your intro request",
            subtitle = "You are now connected with the Head of Product at Kestrel Data.",
            timeAgo = "34m ago",
            category = "requests",
            avatarUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuC5OeyMEslPGT6LN2TaGyMNbSxK1e7ecfLtrisrlVLePYOnVMA9wM5skYgPkPl2Fr1lCnS-bbGNDdIoT3ftwssXzdsvhRUptDG0PtJPkr0Pcy7_mAnHyuVosaa2ZTQBqZ0epdJpdXGGC3YU2xPvTXP7qGfnCm-jV0O2i1njykmbIOFdLppu4p7RwD7FV7i6jFRPstuclZzPWLYV2LuzoyjxH584CvLN0OUvaIVF4ovRnz1STG456n-pog",
            isUnread = true,
            actionType = NotificationAction.REPLY
        ),
        NotificationItem(
            id = "notif_3",
            title = "3 new attendees match your 'Cofounder' search",
            subtitle = "Including Elena Rostova (Principal ML Architect) and 2 other peers active in Lounge Level 2.",
            timeAgo = "1h ago",
            category = "matches",
            isUnread = false,
            actionType = NotificationAction.VIEW_RECOMMENDATIONS
        ),
        NotificationItem(
            id = "notif_4",
            title = "Welcome to Tech Summit 2026!",
            subtitle = "Your Orbit pass is active. You have 18 high-signal attendee matches in the Lounge today.",
            timeAgo = "3h ago",
            category = "system",
            isUnread = false,
            actionType = NotificationAction.VIEW_PROFILE
        )
    )

    private val _notifications = MutableStateFlow(initialNotifications)
    val notifications: StateFlow<List<NotificationItem>> = _notifications.asStateFlow()

    // Domain Interests for Onboarding Step 1
    val initialDomains = listOf(
        DomainInterest("d_ai", "AI & Foundation Models", "tech", true),
        DomainInterest("d_saas", "B2B SaaS", "venture", true),
        DomainInterest("d_infra", "Developer & Cloud Infra", "tech", true),
        DomainInterest("d_agents", "Agentic Systems", "tech", true),
        DomainInterest("d_security", "Cybersecurity & Identity", "tech", false),
        DomainInterest("d_data", "Real-time Data & ETL", "tech", false),
        DomainInterest("d_fintech", "Fintech & Embedded Finance", "venture", false),
        DomainInterest("d_seed", "Early-Stage Seed Venture", "venture", true),
        DomainInterest("d_bio", "BioTech & HealthTech", "venture", false),
        DomainInterest("d_product", "Product Management & UX", "design", true),
        DomainInterest("d_hardware", "Robotics & Edge Hardware", "tech", false),
        DomainInterest("d_open_source", "Open Source Monetization", "venture", false)
    )

    private val _domains = MutableStateFlow(initialDomains)
    val domains: StateFlow<List<DomainInterest>> = _domains.asStateFlow()

    fun toggleDomain(id: String) {
        _domains.update { list ->
            list.map { if (it.id == id) it.copy(isSelected = !it.isSelected) else it }
        }
    }

    // Intent Anchors for Onboarding Step 2
    val initialIntents = listOf(
        IntentAnchor("i_cofounder", "Cofounder", "Technical or commercial partner for early venture", "Handshake", true),
        IntentAnchor("i_customers", "Customers / Design Partners", "Find early pilot testers and enterprise feedback", "Groups", true),
        IntentAnchor("i_investors", "Investors / Angels", "Pre-seed to Series A funding for scaling", "TrendingUp", true),
        IntentAnchor("i_mentors", "Technical Mentor", "Architecture reviews from senior leaders", "Psychology", false),
        IntentAnchor("i_talent", "Senior Engineers", "High-caliber engineers and product designers", "PersonAdd", true),
        IntentAnchor("i_partners", "Partnerships & Integrations", "APIs, distribution, and strategic alliances", "Hub", false),
        IntentAnchor("i_peers", "Peer Founders", "War stories, founder sanity, and mastermind discussions", "Forum", false),
        IntentAnchor("i_feedback", "Product Feedback", "Live teardown of roadmap and pitch deck", "RateReview", false)
    )

    private val _intents = MutableStateFlow(initialIntents)
    val intents: StateFlow<List<IntentAnchor>> = _intents.asStateFlow()

    fun toggleIntent(id: String) {
        _intents.update { list ->
            list.map { if (it.id == id) it.copy(isSelected = !it.isSelected) else it }
        }
    }

    // Offerings for Onboarding Step 3
    val initialOfferings = listOf(
        OfferingItem("o_ai_arch", "AI / ML Architecture", "LLM pipelines, model fine-tuning, retrieval", "Psychology", 48, true),
        OfferingItem("o_dist_sys", "Distributed Systems & Cloud", "Kubernetes, latency optimization, GPUs", "Storage", 36, false),
        OfferingItem("o_gtm", "Early GTM & Marketing", "Developer adoption, positioning, enterprise pilots", "RocketLaunch", 52, true),
        OfferingItem("o_ux", "Product & UX Direction", "Design systems, user testing, workflows", "DesignServices", 29, false),
        OfferingItem("o_pitch", "Fundraising & Pitch Strategy", "Deck reviews, investor warm intros, term sheets", "AccountBalance", 34, true),
        OfferingItem("o_sales", "Enterprise Sales & Procurement", "Closing 6-figure contracts, SOC2 compliance", "Paid", 18, false),
        OfferingItem("o_hiring", "Hiring & Culture", "Engineering leveling, equity grants, sourcing", "Badge", 22, false),
        OfferingItem("o_legal", "Operations & Legal / IP", "Incorporation, patents, IP assignment", "Gavel", 15, false)
    )

    private val _offerings = MutableStateFlow(initialOfferings)
    val offerings: StateFlow<List<OfferingItem>> = _offerings.asStateFlow()

    fun toggleOffering(id: String) {
        _offerings.update { list ->
            list.map { if (it.id == id) it.copy(isSelected = !it.isSelected) else it }
        }
    }

    // Toggle saved / bookmark
    fun toggleSaveAttendee(id: String) {
        _attendees.update { list ->
            list.map { if (it.id == id) it.copy(isSaved = !it.isSaved) else it }
        }
    }

    // Send connection request
    fun sendConnectionRequest(attendeeId: String, note: String, meetLocation: String) {
        _attendees.update { list ->
            list.map {
                if (it.id == attendeeId) it.copy(connectionStatus = ConnectionStatus.REQUESTED) else it
            }
        }
    }

    // Accept intro request
    fun acceptIntro(introId: String) {
        val intro = _pendingIntros.value.find { it.id == introId }
        if (intro != null) {
            _attendees.update { list ->
                list.map {
                    if (it.id == intro.attendee.id) it.copy(connectionStatus = ConnectionStatus.CONNECTED) else it
                }
            }
            _pendingIntros.update { list -> list.filter { it.id != introId } }
            // Add notification
            _notifications.update { list ->
                listOf(
                    NotificationItem(
                        id = "notif_acc_${System.currentTimeMillis()}",
                        title = "Connected with ${intro.attendee.name}",
                        subtitle = "You can now message directly or meet at ${intro.attendee.status}",
                        timeAgo = "Just now",
                        category = "requests",
                        avatarUrl = intro.attendee.avatarUrl,
                        isUnread = false,
                        actionType = NotificationAction.REPLY
                    )
                ) + list
            }
        }
    }

    // Decline intro request
    fun declineIntro(introId: String) {
        _pendingIntros.update { list -> list.filter { it.id != introId } }
    }

    // Mark all notifications read
    fun markAllNotificationsRead() {
        _notifications.update { list ->
            list.map { it.copy(isUnread = false) }
        }
    }

    // Organizer Dashboard data
    val funnelStages = listOf(
        FunnelStage("01", "Scan Event Badge", "2,150", "Attendees Scanned", "100%", null, 1.0f),
        FunnelStage("02", "Landing Page Viewed", "1,890", "Opened Orbit Web/App", "87.9%", "-12.1%", 0.88f),
        FunnelStage("03", "One-Tap Sign In", "1,620", "Auth Completed", "75.3%", "-14.3%", 0.75f),
        FunnelStage("04", "Onboarding Complete", "1,420", "3-Step Intent Set", "66.0%", "-12.3%", 0.66f),
        FunnelStage("05", "Recommendations Viewed", "1,280", "Discovered Peers", "59.5%", "-9.8%", 0.60f),
        FunnelStage("06", "Outbound Intro Sent", "980", "Action Initiated", "45.6%", "-23.4%", 0.46f),
        FunnelStage("07", "Mutual In-Person Connection", "742 pairs", "2,690 Accepted Links", "34.5%", "-24.3%", 0.35f)
    )

    val liveHotspots = listOf(
        Hotspot("Orbit Lounge Level 2", "Primary Networking Hub", 245, "High density"),
        Hotspot("Demo Floor Stage Area", "Founder & Investor Demos", 180, "Active demos"),
        Hotspot("Hallway B (Between Stages)", "Casual hallway track", 140, "Steady flow"),
        Hotspot("Stage A Terrace", "Keynote transition lounge", 95, "Peak break")
    )

    val liveMatchFeed = listOf(
        LiveMatchPair(
            personA = "Aisha K.",
            roleA = "VP Infra @ ScaleGrid",
            avatarA = "https://lh3.googleusercontent.com/aida-public/AB6AXuCFgQ2tPJcDMmZWVAJkmUsQFsZhXcqHT8rLMMpOFvbwdlMxNP4klXPg6rENE3ycUrRXGj5V7eGX4Y_CE33VgxBfQBwAHoD6Ktn6hERZvFZiu88nhcK1M5FSGh3Mj-DC3Zd5l9v-T0mVXIwl7ZhSkZQlpNO9ZwD8JNWT1ZashwLDmhJOXQVEBB6wRwshvTcFMVqNYJ6gAFFfI0bkx6mo-3v3cVxtl8MY0CqXks7alX-UgdlJ6O5YT3GkwQ",
            personB = "Elena V.",
            roleB = "CEO @ Vesper AI",
            avatarB = "https://lh3.googleusercontent.com/aida/AEtjO1VGz9UpI1h0zf7T_H62cSBOd9fEyEuCCCUXctjGnjQTLf95xoi6Pr8TQhPuU6nt3p4GfptZlGBPXYNtlA9ZnVoBvoMXLHS6w_-z1ZES7Djire2D3wYn8W7Pm9GisqI-tusj_dQEf7dxM-hddTgBOkc2-uY6OimJZ-TShJYUVqrfxXcvLtDvqEcmWEej2Zr01LVrZ2DlhM4P4ibqHA3HV6aRbWduls5-hvsaqXVRTSpdtdxVuUrhBA6e8SSJ",
            affinity = "96% Synergy",
            topic = "GPU Optimization ↔ Agent Grounding",
            location = "Orbit Lounge · Table 4",
            timeAgo = "4m ago"
        ),
        LiveMatchPair(
            personA = "Marcus C.",
            roleA = "Partner @ Horizon",
            avatarA = "https://lh3.googleusercontent.com/aida-public/AB6AXuCiDvnOm1WSSbErhYpRDaXr6cL5oE6rkNga9zk3jqGU2nSFSRjK3rddT08mB2jw7O5mXEkQhdEZokwpYZF6x1qAcBFWZqA_jmQSdwOWOzGNOwHI1uKwziL0A8Ef7dQSU9zOxaU62y2COhddePij7kRn7IAoscWQQN23QT3rmYuP__vkn_w_vk8G80A_zR6kpvAlgDHZJTdyXNPa_UANWNAbY-OlSxeHApbGeVRp_6Xo5OWyR_RKkNRflg",
            personB = "David V.",
            roleB = "Head of Product @ Kestrel",
            avatarB = "https://lh3.googleusercontent.com/aida-public/AB6AXuC5OeyMEslPGT6LN2TaGyMNbSxK1e7ecfLtrisrlVLePYOnVMA9wM5skYgPkPl2Fr1lCnS-bbGNDdIoT3ftwssXzdsvhRUptDG0PtJPkr0Pcy7_mAnHyuVosaa2ZTQBqZ0epdJpdXGGC3YU2xPvTXP7qGfnCm-jV0O2i1njykmbIOFdLppu4p7RwD7FV7i6jFRPstuclZzPWLYV2LuzoyjxH584CvLN0OUvaIVF4ovRnz1STG456n-pog",
            affinity = "91% Synergy",
            topic = "Seed Round Discussion ↔ Data Infrastructure",
            location = "VIP Terrace",
            timeAgo = "11m ago"
        ),
        LiveMatchPair(
            personA = "Sarah L.",
            roleA = "Research @ Cerebral",
            avatarA = "https://lh3.googleusercontent.com/aida-public/AB6AXuCXA-CTNoF3D-UrtgM7EaMXSuns9LFrG0cVbTCtj12itUATAZdTTVEQ7HHHHF04ZNhQZeThOGOKxGZsSisikIdLqdpSZdsfLIGIencJTq6P1LCP_4FNnibvFXq2hG3D7IA2yZiHUGvS6uDzaPfLqaPJ0TJJBdxzAC2884mrx81HbW9zAanAFfei4V3b7NSoDrstUynBe0AgS-tEVZz7sEX-fUaSwIBSIGPsrtGEuCJ5InrPkGjqf1rp5g",
            personB = "Elena R.",
            roleB = "ML Architect @ Synthetix",
            avatarB = "https://lh3.googleusercontent.com/aida-public/AB6AXuA53-QVUy-lydHRLIYcqhnEpDKS6SNihTtM2TYKmHen23nJP7Yy7lohMoAV1flUzSQKkMkmvFPAAYo6EEjPGagXKuFhAoJ1vufYZ0BQ94QxonVrnnRAwnCbjG6PXNsRG2aBznyqvXhYERZ_cFaTJTMLMtC7JXtQC07QwAD0GQcghAQrohT_4v6xK37u2WZ55IEUL9Tmp3vkiI5zlrung0-LzuruJN2rnnIk9Q3DCEhYWE8p-oHBpD4kzw",
            affinity = "94% Synergy",
            topic = "Custom Triton Kernels & Attention Slicing",
            location = "Demo Floor B",
            timeAgo = "19m ago"
        )
    )
}
