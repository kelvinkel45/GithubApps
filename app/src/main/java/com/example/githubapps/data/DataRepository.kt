package com.example.githubapps.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.githubapps.data.network.ApiService
import com.example.githubapps.data.response.DetailUserResponse
import com.example.githubapps.data.response.UserResponse
import com.example.githubapps.util.Resource

class DataRepository private constructor(
    private val apiService: ApiService
){

    companion object {
        @Volatile
        private var instance: DataRepository? = null
        fun getInstance(
            apiService: ApiService
        ): DataRepository =
            instance ?: synchronized(this) {
                instance ?: DataRepository(apiService)
            }.also { instance = it }
    }

//    fun getListUser(username : String) : LiveData<Resource<List<UserResponse>>> = liveData{
//        emit(Resource.Loading())
//        try {
//            val response = apiService.getSearchUsers(username)
//            emit(Resource.Success(response.listUser))
//        }catch (e : Exception){
//            emit(Resource.Error(e.message.toString()))
//        }
//    }
    suspend fun getListUser(username : String) : Resource<List<UserResponse>> {
        //emit(Resource.Loading())
        return try {
            val response = apiService.getSearchUsers(username)
            Resource.Success(response.listUser)
        }catch (e : Exception){
            Resource.Error(e.message.toString())
        }
    }

    fun getDetailUser(username: String) : LiveData<Resource<DetailUserResponse>> = liveData {
        emit(Resource.Loading())
        try {
            val response = apiService.getDetailUser(username)
            emit(Resource.Success(response))
        }catch (e : Exception){
            emit(Resource.Error(e.message.toString()))
        }
    }

    fun getListFollowers(username: String) : LiveData<Resource<List<UserResponse>>> = liveData {
        emit(Resource.Loading())
        try {
            val response = apiService.getListFollowers(username)
            emit(Resource.Success(response))
        }catch (e : Exception){
            emit(Resource.Error(e.message.toString()))
        }
    }

    fun getListFollowing(username: String) : LiveData<Resource<List<UserResponse>>> = liveData {
        emit(Resource.Loading())
        try {
            val response = apiService.getListFollowing(username)
            emit(Resource.Success(response))
        }catch (e : Exception){
            emit(Resource.Error(e.message.toString()))
        }
    }

}