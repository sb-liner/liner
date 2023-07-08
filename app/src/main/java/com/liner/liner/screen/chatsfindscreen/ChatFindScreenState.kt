package com.liner.liner.screen.chatsfindscreen

sealed class ChatFindScreenState {
    object Loading : ChatFindScreenState()
    object Loaded : ChatFindScreenState()
    data class Matched(val matchedUserId:String) : ChatFindScreenState()
    object Connected : ChatFindScreenState()
    data class Error(val errorMessage:String) : ChatFindScreenState()
}