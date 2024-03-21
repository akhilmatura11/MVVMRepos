package com.mvvm.repos.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Repos(
    @PrimaryKey
    val id: Long,
    val name: String?,
    val fullName: String?,
    val description: String?,
    val avatarUrl: String?
) : Parcelable

data class Repository(
    val id: Long,
    val name: String?,

    @Json(name = "full_name")
    val full_name: String?,

    val description: String?,
    val owner: OwnerInfo
)

data class OwnerInfo(
    @Json(name = "avatar_url")
    val avatar_url: String?
)

fun List<Repository>.toRepoListMapper(): List<Repos> {
    val reposList = mutableListOf<Repos>()
    this.map {
        reposList.add(it.toReposMapper())
    }
    return reposList
}

fun Repository.toReposMapper(): Repos {
    return Repos(
        id = this.id,
        name = this.name,
        fullName = this.full_name,
        description = this.description,
        avatarUrl = this.owner.avatar_url
    )
}
