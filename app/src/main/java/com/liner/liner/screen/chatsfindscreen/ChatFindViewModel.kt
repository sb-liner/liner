package com.liner.liner.screen.chatsfindscreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChatFindViewModel @Inject constructor():ViewModel(){

    var state = mutableStateOf<ChatFindScreenState>(ChatFindScreenState.Loading)

    init {
        state.value = ChatFindScreenState.Connected
    }
}