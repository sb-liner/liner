package com.liner.liner

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.liner.liner.navigation.MyAppNavHost
import com.liner.liner.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseScreen(){
    val navController = rememberNavController()

    val items = listOf(
        Screen.Main,
        Screen.Login,
        Screen.Login,
        Screen.Login,
        Screen.Login,
    )

    Scaffold(
        bottomBar = {
            BottomNavigation (
                backgroundColor = Color.White,
                elevation = 0.dp,
                modifier = Modifier.height(80.dp)
                    ){
                Row(modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center) {

                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination
                    items.forEach { screen ->
                        IconButton(onClick = {
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
                        }) {
                            Icon(screen.icon, contentDescription = "content description")
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        MyAppNavHost(modifier = Modifier.padding(innerPadding),navController)
    }
}