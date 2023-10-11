package com.example.githubapps.ui.follow

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.githubapps.data.DataRepository
import com.example.githubapps.data.response.UserResponse
import com.example.githubapps.util.Resource

class FollowViewModel (private val dataRepository: DataRepository): ViewModel() {

    private var _position = 0
    private var _username = ""

    fun setPosition(position: Int){
        _position = position
    }

    fun setUsername(userName: String){
        _username = userName
    }

    fun getListFollowData() : LiveData<Resource<List<UserResponse>>>{
        return if (_position == 1){
            dataRepository.getListFollowing(_username)
        }else{
            dataRepository.getListFollowers(_username)
        }
    }

}