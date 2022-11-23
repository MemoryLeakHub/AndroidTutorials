package com.vrashkov.shared_image

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

@HiltViewModel
class SharedViewModel
@Inject
constructor(): ViewModel(){

    val sharedImageFlow: MutableSharedFlow<SharedImageDataEvent> = MutableSharedFlow(replay = Int.MAX_VALUE)
    val sharedImageAnimateEventFlow: MutableSharedFlow<SharedImageAnimateEvent> = MutableSharedFlow(replay = Int.MAX_VALUE)
}