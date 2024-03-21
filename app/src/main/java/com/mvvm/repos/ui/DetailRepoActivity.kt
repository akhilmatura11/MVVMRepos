package com.mvvm.repos.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.mvvm.repos.R
import com.mvvm.repos.data.model.Repos
import com.mvvm.repos.databinding.ActivityDetailRepoBinding
import com.bumptech.glide.Glide

class DetailRepoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityDetailRepoBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_detail_repo
        )
        setContentView(binding.root)

        val repo = intent.getParcelableExtra<Repos>("repo")

        binding.lifecycleOwner = this
        binding.repo = repo

        Glide.with(this@DetailRepoActivity)
            .load(repo?.avatarUrl)
            .into(binding.detailAvatarImage)
    }
}