package com.example.githubapps.data.network

import com.example.githubapps.data.response.DetailUserResponse
import com.example.githubapps.data.response.ListUserResponse
import com.example.githubapps.data.response.UserResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    suspend fun getSearchUsers(
        @Query("q") username : String
    ) : ListUserResponse

    @GET("users/{username}")
    suspend fun getDetailUser(
       @Path("username") username: String
    ) : DetailUserResponse

    @GET("users/{username}/followers")
    suspend fun getListFollowers(
        @Path("username") username: String
    ) : List<UserResponse>

    @GET("users/{username}/following")
    suspend fun getListFollowing(
        @Path("username") username: String
    ) : List<UserResponse>
}