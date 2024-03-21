package com.mvvm.repos.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mvvm.repos.R
import com.mvvm.repos.data.model.Repos
import com.mvvm.repos.databinding.ItemRepoBinding
import com.mvvm.repos.viewModel.MainViewModel
import com.bumptech.glide.Glide

class MainRecyclerAdapter(private val viewModel: MainViewModel) :
    ListAdapter<Repos, MainRecyclerAdapter.MainRecyclerViewHolder>(DiffCallback()) {
    private class DiffCallback : DiffUtil.ItemCallback<Repos>() {
        override fun areItemsTheSame(oldItem: Repos, newItem: Repos) =
            oldItem.fullName == newItem.fullName

        override fun areContentsTheSame(oldItem: Repos, newItem: Repos) =
            oldItem == newItem && oldItem.fullName == newItem.fullName
    }

    private var repoList = mutableListOf<Repos>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainRecyclerViewHolder {
        val binding: ItemRepoBinding = DataBindingUtil
            .inflate(
                LayoutInflater.from(parent.context), R.layout.item_repo, parent, false
            )
        return MainRecyclerViewHolder(binding, viewModel)
    }

    override fun onBindViewHolder(holder: MainRecyclerViewHolder, position: Int) {
        holder.bind(repoList[position])
    }

    override fun getItemCount() = repoList.size

    fun updateList(reposList: List<Repos>) {
        this.repoList.clear()
        this.repoList.addAll(reposList)
        submitList(reposList)
    }

    class MainRecyclerViewHolder(
        private val binding: ItemRepoBinding, private val viewModel: MainViewModel
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(repo: Repos) {
            binding.viewModel = viewModel
            binding.repo = repo
            binding.executePendingBindings()

            Glide.with(binding.root.context)
                .load(repo.avatarUrl)
                .into(binding.avatarImage)
        }
    }
}
