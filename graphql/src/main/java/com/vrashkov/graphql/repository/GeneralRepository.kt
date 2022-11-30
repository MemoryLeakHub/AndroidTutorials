package com.vrashkov.graphql.repository

import com.apollographql.apollo3.ApolloCall
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Optional
import com.vrashkov.graphql.GetLaunchesListQuery
import com.vrashkov.graphql.base.RequestResult
import com.vrashkov.graphql.models.LaunchData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GeneralRepository @Inject constructor(
    private val webService: ApolloClient
) {
    suspend fun getLaunchesList(after: String?) : RequestResult<List<LaunchData>> = withContext(Dispatchers.IO) {
        try {
            val data = webService.query(GetLaunchesListQuery(after = Optional.present(after))).execute()

            val launchesList = mutableListOf<LaunchData>()

            data.data?.launches?.let { launchesData ->
                launchesData.launches.forEach {
                    it?.let { launch ->
                        launch.launchTile.mission?.let { mission ->
                            launchesList.add(
                                LaunchData(
                                    name = mission.name ?: "",
                                    cursor = launchesData.cursor
                                )
                            )
                        }
                    }
                }
            }

            return@withContext RequestResult.Success(launchesList)
        } catch (e: Exception) {
            return@withContext (RequestResult.Error(e))
        }
    }
}