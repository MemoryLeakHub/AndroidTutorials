package com.vrashkov.graphql.ui.list

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vrashkov.graphql.base.DataState
import com.vrashkov.graphql.usecase.GetLaunchesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LaunchVM
@Inject
constructor(
    private val launchesUseCase: GetLaunchesUseCase
): ViewModel(){
    val viewState: MutableState<LaunchState> = mutableStateOf(LaunchState())

    val _navigationEventFlow: MutableSharedFlow<LaunchNavigationEvent> = MutableSharedFlow(replay = 0)
    val navigationEventFlow: SharedFlow<LaunchNavigationEvent> = _navigationEventFlow

    init {
        getLaunchesList()
    }

    fun onTriggerEvent(event: LaunchEvent){
        when (event) {
            is LaunchEvent.LoadMoreClick -> { viewModelScope.launch {
                    val lastLaunch = viewState.value.launchesList.last()
                    getLaunchesList(lastLaunch.cursor)
                }
            }
        }
    }

    private fun getLaunchesList(cursor: String? = null) {
        viewModelScope.launch {
            launchesUseCase.execute(cursor).collect { data ->
                when(data) {
                    is DataState.Data -> {
                        val list = viewState.value.launchesList.toMutableList()
                        list.addAll(data.data!!)
                        viewState.value = viewState.value.copy(launchesList = list)
                    }
                    is DataState.Error -> {}
                    is DataState.Loading -> {}
                }
            }
        }
    }
}


