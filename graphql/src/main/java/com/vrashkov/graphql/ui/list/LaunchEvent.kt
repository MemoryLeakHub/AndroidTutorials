package com.vrashkov.graphql.ui.list

sealed class LaunchEvent {
    object LoadMoreClick: LaunchEvent()
}