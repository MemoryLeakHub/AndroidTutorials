package com.vrashkov.graphql.base

sealed class ProgressBarState{
    
    object Loading: ProgressBarState()
    
    object Gone: ProgressBarState()
}