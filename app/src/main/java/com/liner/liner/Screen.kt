package com.liner.liner

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Login
import androidx.compose.material.icons.filled.NextPlan
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, @StringRes val resourceId: Int, val icon: ImageVector) {
    object Main : Screen("main", R.string.main, Icons.Default.NextPlan)
    object Login : Screen("login", R.string.login, Icons.Default.Login)
}