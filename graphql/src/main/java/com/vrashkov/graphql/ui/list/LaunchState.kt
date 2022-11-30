package com.vrashkov.graphql.ui.list

import com.vrashkov.graphql.models.LaunchData

data class LaunchState(
    val launchesList: List<LaunchData> = listOf()
)