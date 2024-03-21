package com.mvvm.repos.data.remote

import com.mvvm.repos.data.model.Repository
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {

    @GET("/users/{user}/repos")
    suspend fun fetchRepositories(@Path("user") user: String): Response<List<Repository>>
}