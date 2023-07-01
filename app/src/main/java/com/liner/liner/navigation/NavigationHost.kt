package com.liner.liner.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.liner.liner.screen.chatsfindscreen.ChatFindScreen
import com.liner.liner.screen.mainscreen.MainScreen
import com.liner.liner.screen.mbtiscreen.MbtiScreen

@Composable
fun MyAppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.ChatFind.route
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Main.route) {
            MainScreen()
        }
        composable(Screen.Login.route) {

        }
        composable(Screen.Mbti.route) {
            MbtiScreen()
        }
        composable(Screen.ChatFind.route) {
            ChatFindScreen()
        }
    }
}

