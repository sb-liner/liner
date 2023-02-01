package com.liner.liner.screen.mainscreen

import android.app.Application
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
            delay(2000)
            fetchData()
            state.value = MainScreenState.Loaded
        }
    }

    private fun fetchData() {
        userList.add(User("pichu", emptyList(), R.drawable.pichu))
        userList.add(User("girl", emptyList(), R.drawable.dummy_image))
    }

    fun swipeRight(){
        userList.removeFirst()
    }

}