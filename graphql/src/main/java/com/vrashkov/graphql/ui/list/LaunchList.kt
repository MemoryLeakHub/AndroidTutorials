package com.vrashkov.graphql.ui.list

import android.widget.Space
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun LaunchListScreen(navController: NavController) {

    val viewModel = hiltViewModel<LaunchVM>()

    val onTriggerEvents = viewModel::onTriggerEvent
    val viewState = viewModel.viewState.value

    LaunchedEffect(Unit) {
        viewModel.navigationEventFlow.collect {
            when (it) {
                is LaunchNavigationEvent.NavigateToSingleLaunch -> {

                }
            }
        }
    }

    LaunchList(viewModel, viewState, onTriggerEvents)
}

@Composable
private fun LaunchList(
    viewModel: LaunchVM,
    viewState: LaunchState,
    onTriggerEvents: (LaunchEvent) -> Unit,
) {

    Column (Modifier.fillMaxSize()){
        LazyColumn (Modifier.fillMaxWidth().weight(1f)) {
            items(viewState.launchesList) { data ->
                Spacer(Modifier.height(10.dp))
                Text(data.name)
                Spacer(Modifier.height(10.dp))
            }
        }
        Button(
            onClick = {
                onTriggerEvents(LaunchEvent.LoadMoreClick)
            }
        ) {
            Text("Load More")
        }
    }
}