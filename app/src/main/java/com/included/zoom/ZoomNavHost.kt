package com.included.zoom

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.included.zoom.meeting.MeetingScreen
import com.included.zoom.start.StartScreen
import kotlinx.serialization.Serializable

@Serializable
object Start

@Serializable
data class Meeting(val meetingId: String, val meetingPassword: String)

@Composable
fun ZoomNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: Any = Start,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        composable<Start> { backStackEntry ->
            StartScreen(onNavigateToMeeting = { meetingId, meetingPassword ->
                navController.navigate(
                    route = Meeting(meetingId = meetingId, meetingPassword = meetingPassword)
                )
            })
        }

        composable<Meeting> { backStackEntry ->
            val route = backStackEntry.toRoute<Meeting>()
            MeetingScreen(
                meetingId = route.meetingId,
                meetingPassword = route.meetingPassword,
            )
        }
    }
}