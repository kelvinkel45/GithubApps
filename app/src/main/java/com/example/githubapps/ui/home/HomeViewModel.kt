package com.example.githubapps.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubapps.data.DataRepository
import com.example.githubapps.util.Resource
import com.example.githubapps.data.response.UserResponse
import kotlinx.coroutines.launch

class HomeViewModel (private val dataRepository: DataRepository) : ViewModel() {

    private val _listUser = MutableLiveData<Resource<List<UserResponse>>>()
    val listUser : LiveData<Resource<List<UserResponse>>> = _listUser

    init {
        getListUsers()
    }

    fun getListUsers (username : String? = null) {
        _listUser.value = Resource.Loading()
        viewModelScope.launch{
            _listUser.value = dataRepository.getListUser(username?:"test")
        }
    }
}