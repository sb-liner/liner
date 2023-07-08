package com.liner.liner.screen.mainscreen

import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.liner.liner.R
import com.liner.liner.data.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(application: Application) : AndroidViewModel(application) {

    var state = mutableStateOf<MainScreenState>(MainScreenState.Loading)

    var userList = mutableStateListOf<User>()

    init {
        viewModelScope.launch {
            delay(500)
            fetchData()
            state.value = MainScreenState.Loaded(
                userList.firstOrNull(),
                if (userList.size > 1) userList[1] else null
            )
        }
    }

    private fun fetchData() {
        repeat(5) {
            userList.add(User("pichu$it", emptyList(), R.drawable.pichu, 3))
            userList.add(User("kim$it", emptyList(), R.drawable.dummy_image, 20))
        }
    }

    fun like() {
        userList.removeFirst()
        state.value = MainScreenState.Loaded(
            userList.firstOrNull(),
            if (userList.size > 1) userList[1] else null
        )
    }

    fun nope() {
        userList.removeFirst()
        state.value = MainScreenState.Loaded(
            userList.firstOrNull(),
            if (userList.size > 1) userList[1] else null
        )
    }

}