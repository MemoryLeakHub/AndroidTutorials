package com.vrashkov.login_tests.usecase

import com.vrashkov.login_tests.base.DataState
import com.vrashkov.login_tests.base.ProgressBarState
import com.vrashkov.login_tests.base.RequestResult
import com.vrashkov.login_tests.network.mapped.PostResult
import com.vrashkov.login_tests.network.mapped.UserResult
import com.vrashkov.login_tests.repository.GeneralRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ListUserPostsUseCase @Inject constructor(
    private val generalRepository: GeneralRepository,
) {
    fun execute(): Flow<DataState<out List<UserPost>>> = flow {

        emit(DataState.Loading(progressState = ProgressBarState.Loading))
        val userPostList: MutableList<UserPost> = mutableListOf()
        // we call for all the users
        // so we can use it when getting the posts
        val users = generalRepository.users()

        when (users) {
            is RequestResult.Success -> {
                when (val posts = generalRepository.posts()) {
                    is RequestResult.Success -> {
                        emit(DataState.Data(data = userPostList))
                    }
                    is RequestResult.Error -> {
                        emit(DataState.Error(error = posts.exception))
                    }
                }
            }
            is RequestResult.Error -> {
                emit(DataState.Error(error = users.exception))
            }
        }

    }
}
data class UserPost(
    val post: PostResult,
    val user: UserResult
)