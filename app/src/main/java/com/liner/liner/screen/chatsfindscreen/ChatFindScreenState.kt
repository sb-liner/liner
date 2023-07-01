package com.liner.liner.screen.chatsfindscreen

sealed class ChatFindScreenState {
    object Loading : ChatFindScreenState()
    object Loaded : ChatFindScreenState()
    object Connected : ChatFindScreenState()
}