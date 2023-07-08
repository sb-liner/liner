package com.liner.liner.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fireplace
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.liner.liner.navigation.MyAppNavHost
import com.liner.liner.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseScreen() {
    val navController = rememberNavController()

    val items = listOf(
        Screen.ChatFind,
        Screen.Main,
        Screen.Login,
        Screen.Mbti,
    )

    Box(
        modifier = Modifier
            .windowInsetsPadding(WindowInsets.statusBars)
            .windowInsetsPadding(WindowInsets.navigationBars)
    ) {
        Scaffold(
            topBar = {
                TopAppBar(elevation = 0.dp, backgroundColor = MaterialTheme.colors.background) {
                    Icon(
                        Icons.Default.Fireplace,
                        contentDescription = "AppIcon",
                        tint = MaterialTheme.colors.primary
                    )
                    Text(text = "liner", color = MaterialTheme.colors.primary)
                }
            },
            bottomBar = {
                BottomNavigation(
                    backgroundColor = Color.White,
                    elevation = 8.dp,
                    modifier = Modifier.height(55.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {

                        val navBackStackEntry by navController.currentBackStackEntryAsState()
                        val currentDestination = navBackStackEntry?.destination?.route
                        items.forEach { screen ->
                            BottomNavigationItem(
                                onClick = {
                                    navController.navigate(screen.route) {
                                        // Pop up to the start destination of the graph to
                                        // avoid building up a large stack of destinations
                                        // on the back stack as users select items
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        // Avoid multiple copies of the same destination when
                                        // reselecting the same item
                                        launchSingleTop = true
                                        // Restore state when reselecting a previously selected item
                                        restoreState = true
                                    }
                                },
                                icon = {
                                    Icon(
                                        screen.icon,
                                        contentDescription = "content description"
                                    )
                                },
                                selected = currentDestination == screen.route,
                                selectedContentColor = MaterialTheme.colors.primary,
                                unselectedContentColor = Color.Gray,
                            )
                        }
                    }
                }
            }
        ) { innerPadding ->
            var bottomPadding = 0.dp
            bottomPadding =
                if (WindowInsets.ime.asPaddingValues()
                        .calculateBottomPadding() > innerPadding.calculateBottomPadding()
                ) {
                    0.dp
                } else {
                    innerPadding.calculateBottomPadding()
                }
            MyAppNavHost(
                modifier = Modifier.padding(
                    top = innerPadding.calculateTopPadding(),
                    start = innerPadding.calculateStartPadding(LayoutDirection.Ltr),
                    end = innerPadding.calculateEndPadding(LayoutDirection.Ltr),
                    bottom = bottomPadding
                ), navController
            )
        }
    }
}