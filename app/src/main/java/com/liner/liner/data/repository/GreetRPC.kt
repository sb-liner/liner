package com.liner.liner.data.repository

import android.net.Uri
import android.util.Log
import io.grpc.ManagedChannelBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor
import org.liner.chat.grpc.ChatServiceGrpcKt
import org.liner.chat.grpc.chatRequest
import java.io.Closeable

private const val TAG = "GreetRPC"

class GreetRCP(uri: Pair<String,Int>) : Closeable {
    private val channel = let {
        val builder = ManagedChannelBuilder.forAddress(uri.first, uri.second)

        builder.usePlaintext()

        builder.executor(Dispatchers.IO.asExecutor()).build()
    }

    private val greeter = ChatServiceGrpcKt.ChatServiceCoroutineStub(channel)

    suspend fun sayHello(name: String) {
        kotlin.runCatching {
            val request = chatRequest { this.name = name }
            greeter.helloChatService(request)
        }.onSuccess { response ->
            print(response.id)
            Log.d(TAG, "sayHello: ${response.id}")
        }.onFailure { e ->
            e.printStackTrace()
        }
    }

    override fun close() {
        channel.shutdownNow()
    }
}