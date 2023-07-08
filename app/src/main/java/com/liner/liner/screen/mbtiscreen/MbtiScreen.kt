package com.liner.liner.screen.mbtiscreen

import androidx.compose.material3.Slider
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import com.liner.liner.screen.mainscreen.MainScreenState
import com.liner.liner.screen.mainscreen.MainViewModel

@Composable
fun MbtiScreen(
    viewModel: MainViewModel = hiltViewModel()
) {

    when (viewModel.state.value) {
        is MainScreenState.Loading -> {}
        is MainScreenState.Loaded -> {}
    }

    MbtiSlider()

}

@Composable
fun MbtiSlider(){

    var sliderValue by remember {
        mutableStateOf(0f)
    }
    Slider(value = sliderValue, onValueChange = { sliderValue_ ->
        sliderValue = sliderValue_
    } )

}