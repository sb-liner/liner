package com.liner.liner.data.repository

import android.util.Log
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import org.liner.chat.grpc.ChatRequest
import org.liner.chat.grpc.ChatResponse
import org.liner.chat.grpc.ChatServiceGrpcKt


private const val TAG = "ChatServiceRPC"

class ChatServiceRPC {
    companion object {
        private const val ADDRESS = "192.168.0.6"
        private const val PORT = 8080
    }

    private var managedChannel: ManagedChannel? = null
    @Synchronized
    private fun getManagedChannel(): ManagedChannel? {
        if (managedChannel == null) {
            managedChannel = ManagedChannelBuilder
                .forAddress(ADDRESS, PORT)
//          .useTransportSecurity() //HTTPS인 경우 사용
                .usePlaintext()
                .build()
        }
        return managedChannel
    }

    suspend fun getMatchedUser(id:String) :ChatResponse? {
        try {
            getManagedChannel()?.let { channel ->
                val stub = ChatServiceGrpcKt.ChatServiceCoroutineStub(channel)
                return stub.getMatchedUserId(ChatRequest.newBuilder().setId(id).build())
            }
        }catch (e:Exception){
            Log.d(TAG, "getMatchedUser: ERROR! $e")
        }
        return null
    }
}

