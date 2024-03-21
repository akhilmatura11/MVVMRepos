package com.mvvm.repos.data

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import com.mvvm.repos.data.local.LocalDataSource
import com.mvvm.repos.data.model.toRepoListMapper
import com.mvvm.repos.data.remote.RemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DataRepository @Inject constructor(
    context: Context,
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) {
    private var createdAt = 0L
    private val expiresInSeconds = 2 * 60 * 60 * 1000L
    private val sharedPref: SharedPreferences = context.getSharedPreferences(
        "MVVMRepos", Context.MODE_PRIVATE
    )

    val responseError = MutableLiveData(false)
    val reposList = localDataSource.getRepos()

    init {
        createdAt = sharedPref.getLong("createdAt", 0L)
    }

    suspend fun getRepos(userName: String) = withContext(Dispatchers.IO) {
        responseError.postValue(false)
        var timerValue = createdAt + (expiresInSeconds * 1000) - System.currentTimeMillis()
        localDataSource.getReposList().let {
            if (it.isEmpty()
                || timerValue <= 0
            ) {
                fetchAndCache(userName)
                timerValue = expiresInSeconds
            }

            timerForNextCall(timerValue, userName)
        }
    }

    private suspend fun timerForNextCall(timerValue: Long, userName: String) {
        delay(timerValue)
        fetchAndCache(userName)
        timerForNextCall(expiresInSeconds, userName)
    }

    private suspend fun fetchAndCache(userName: String) {
        val result = remoteDataSource.fetchRepos(userName)
        if (result.isSuccessful) {
            result.body()?.let {
                val repoList = it.toRepoListMapper()
                createdAt = System.currentTimeMillis()
                sharedPref.edit().putLong("createdAt", createdAt).apply()
                localDataSource.insertRepos(repoList)
            }
        } else {
            responseError.postValue(true)
        }
    }
}
