package com.mvvm.repos.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mvvm.repos.data.DataRepository
import com.mvvm.repos.data.model.Repos
import com.mvvm.repos.ui.MainRecyclerAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dataRepository: DataRepository
) : ViewModel() {
    private val adapter = MainRecyclerAdapter(this)
    val reposeError = dataRepository.responseError

    val repoList = dataRepository.reposList
    val onRepoItemClicked = MutableLiveData<Repos>()

    fun getAdapter(): MainRecyclerAdapter = adapter

    init {
        fetchRepos()
    }

    fun fetchRepos() {
        viewModelScope.launch {
            dataRepository.getRepos("Octokit")
        }
    }

    fun onItemClicked(repo: Repos) {
        onRepoItemClicked.value = repo
    }

    fun updateAdapter(reposList: List<Repos>) {
        adapter.updateList(reposList)
    }

}