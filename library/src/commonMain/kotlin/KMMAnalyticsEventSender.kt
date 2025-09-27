package io.github.kotlin.fibonacci

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.analytics.analytics

fun sendHomeScreenViewedEvent() {
    Firebase.analytics.logEvent(
        name = "home_screen_viewed",
        parameters = mapOf(
            "screen_name" to "Home",
            "timestamp" to 2
        )
    )
}