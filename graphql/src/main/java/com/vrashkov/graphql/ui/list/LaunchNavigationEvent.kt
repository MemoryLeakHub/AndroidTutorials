package com.vrashkov.graphql.ui.list

sealed class LaunchNavigationEvent {
    object NavigateToSingleLaunch: LaunchNavigationEvent()
}