package com.liner.liner.data.model

import java.time.LocalDateTime

data class ChatMessage(
    val id:String,
    val dateTime: LocalDateTime,
    val msg:String,
    val status:Int,
    val sender:String,
    val channel:String
)
