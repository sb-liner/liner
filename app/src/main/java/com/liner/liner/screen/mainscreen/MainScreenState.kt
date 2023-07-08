package com.liner.liner.screen.mainscreen

import com.liner.liner.data.model.User

sealed class MainScreenState{
    object Loading : MainScreenState()
    data class Loaded(val firstCardUser: User?, val secondCardUser: User?) : MainScreenState()
}