package com.vrashkov.graphql.usecase

import com.vrashkov.graphql.base.DataState
import com.vrashkov.graphql.base.RequestResult
import com.vrashkov.graphql.models.LaunchData
import com.vrashkov.graphql.repository.GeneralRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetLaunchesUseCase @Inject constructor(private val generalRepository: GeneralRepository) {
    suspend fun execute(curosr: String? = null): Flow<DataState<out List<LaunchData>>> = flow {
        emit(DataState.Loading())
        when (val fetchLaunches = generalRepository.getLaunchesList(curosr)) {
            is RequestResult.Success -> {
                emit(DataState.Data(fetchLaunches.data))
            }
            is RequestResult.Error -> emit(DataState.Error(fetchLaunches.exception))
        }
    }
}