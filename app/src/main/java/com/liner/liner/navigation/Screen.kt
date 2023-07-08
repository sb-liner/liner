package com.liner.liner.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Login
import androidx.compose.material.icons.filled.NextPlan
import androidx.compose.ui.graphics.vector.ImageVector
import com.liner.liner.R

sealed class Screen(val route: String, @StringRes val resourceId: Int, val icon: ImageVector) {
    object ChatFind : Screen("chat_find", R.string.main, Icons.Default.NextPlan)
    object Main : Screen("main", R.string.main, Icons.Default.NextPlan)
    object Login : Screen("login", R.string.login, Icons.Default.Login)
    object Mbti : Screen("mbti", R.string.mbti, Icons.Default.Login)
}