package com.mvvm.repos.data.remote

import com.mvvm.repos.data.model.Repository
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiInterface: ApiInterface) {
    suspend fun fetchRepos(userName: String): Response<List<Repository>> {
        return apiInterface.fetchRepositories(userName)
    }

}
