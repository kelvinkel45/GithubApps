package com.example.githubapps.data.network

import com.example.githubapps.data.response.DetailUserResponse
import com.example.githubapps.data.response.ListUserResponse
import com.example.githubapps.data.response.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    fun getSearchUsers(
        @Query("q") username : String
    ) : Call<ListUserResponse>

    @GET("users/{username}")
    fun getDetailUser(
       @Path("username") username: String
    ) : Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getListFollowers(
        @Path("username") username: String
    ) : Call<List<UserResponse>>

    @GET("users/{username}/following")
    fun getListFollowing(
        @Path("username") username: String
    ) : Call<List<UserResponse>>
}