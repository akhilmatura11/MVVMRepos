package com.mvvm.repos.data.local

import androidx.lifecycle.LiveData
import com.mvvm.repos.data.model.Repos
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val reposDao: ReposDao) {
    fun getRepos(): LiveData<List<Repos>> = reposDao.getRepos()

    fun insertRepos(repoList: List<Repos>) {
        reposDao.deleteAll()
        reposDao.insertRepos(repoList)
    }

    fun getReposList() =
        reposDao.getReposList()

}
