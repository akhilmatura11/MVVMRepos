package com.mvvm.repos.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mvvm.repos.databinding.ActivityMainBinding
import com.mvvm.repos.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val mViewModel by viewModels<MainViewModel>()
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModel()
        bindViews()
    }

    private fun setupViewModel() {
        mViewModel.reposeError.observe(this) {
            if (it) {
                with(binding) {
                    progressBarLayout.visibility = View.GONE
                    errorLayout.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                }
            }
        }
        mViewModel.repoList.observe(this) {
            if (it.isNotEmpty()) {
                with(binding) {
                    progressBarLayout.visibility = View.GONE
                    errorLayout.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE
                    it?.let { mViewModel.updateAdapter(it) }
                }
            }
        }

        mViewModel.onRepoItemClicked.observe(this) {
            val intent = Intent(this@MainActivity, DetailRepoActivity::class.java)
            intent.putExtra("repo", it)
            startActivity(intent)
        }
    }

    private fun bindViews() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            progressBarLayout.visibility = View.VISIBLE
            errorLayout.visibility = View.GONE
            errorRetryButton.setOnClickListener {
                mViewModel.fetchRepos()
                progressBarLayout.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
                errorLayout.visibility = View.GONE
            }
        }

        with(binding.recyclerView) {
            visibility = View.GONE
            adapter = mViewModel.getAdapter()
            addItemDecoration(
                DividerItemDecoration(
                    this@MainActivity,
                    LinearLayoutManager.VERTICAL
                )
            )
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }
}
