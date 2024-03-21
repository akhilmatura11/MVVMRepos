package com.mvvm.repos.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mvvm.repos.data.model.Repos

@Dao
interface ReposDao {
    @Query("Select * from Repos")
    fun getRepos(): LiveData<List<Repos>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRepos(repoList: List<Repos>)

    @Query("Delete from Repos")
    fun deleteAll()

    @Query("Select * from Repos")
    fun getReposList(): List<Repos>
}
