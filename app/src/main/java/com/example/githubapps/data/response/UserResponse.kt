package com.example.githubapps.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserResponse(
    @field:SerializedName("id")
    val id:String,

    @field:SerializedName("login")
    val username:String,

    @field:SerializedName("avatar_url")
    val avatarUrl:String
) : Parcelable

data class ListUserResponse(
    @field:SerializedName("items")
    val listUser: List<UserResponse>
)

data class DetailUserResponse(
    @field:SerializedName("id")
    val id:String,

    @field:SerializedName("login")
    val username:String,

    @field:SerializedName("avatar_url")
    val avatarUrl:String,


    @field:SerializedName("followers_url")
    val followersUrl : String,

    @field:SerializedName("following_url")
    val followingUrl: String,

    @field:SerializedName("followers")
    val followers: String,

    @field:SerializedName("following")
    val following: String,
)
