package com.liner.liner.screen.chatsfindscreen

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.liner.liner.data.model.ChatMessage
import com.liner.liner.data.repository.ChatServiceRPC
import com.liner.liner.data.repository.GreetRCP
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class ChatFindViewModel @Inject constructor():ViewModel(){

    var state = mutableStateOf<ChatFindScreenState>(ChatFindScreenState.Loading)

    val chatMessage = mutableStateListOf<ChatMessage>()

    init {
        //findNewMatch()
        state.value = ChatFindScreenState.Matched("TEST LOGIC")
        repeat(100){
            chatMessage.add(ChatMessage("", LocalDateTime.now(),"TEST LOGIC $it",1,"send","ch"))
        }
    }

    fun sendMsg(textState: String) {
        viewModelScope.launch {
            chatMessage.add(ChatMessage("", LocalDateTime.now(),textState,1,"send","ch"))
        }
    }

    fun findNewMatch(){
        viewModelScope.launch {
            state.value = ChatFindScreenState.Loading
            ChatServiceRPC().getMatchedUser("uifah21klj12hl")?.let {
                state.value = ChatFindScreenState.Matched(it.id)
            }?: kotlin.run {
                state.value = ChatFindScreenState.Error("매칭 중 에러 발생")
            }
        }
    }


}