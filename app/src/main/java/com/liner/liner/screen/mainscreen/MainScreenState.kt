package com.liner.liner.screen.mainscreen

sealed class MainScreenState{
    object Loading : MainScreenState()
    object Loaded : MainScreenState()
}