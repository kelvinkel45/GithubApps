package com.example.githubapps.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubapps.util.Resource
import com.example.githubapps.data.network.ApiClient
import com.example.githubapps.data.response.ListUserResponse
import com.example.githubapps.data.response.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private val _listUser = MutableLiveData<Resource<List<UserResponse>>>()
    val listUser : LiveData<Resource<List<UserResponse>>> = _listUser

    init {
        getListUser()
    }

    fun getListUser(username : String? = null){
        _listUser.value = Resource.Loading(null)
        val client = ApiClient.getApiService().getSearchUsers(username?:"test")
        client.enqueue(object : Callback<ListUserResponse>{
            override fun onResponse(
                call: Call<ListUserResponse>,
                response: Response<ListUserResponse>
            ) {
                if (response.isSuccessful){
                    val data = response.body()?.listUser
                    if (data != null){
                        if (data.isEmpty()){
                            _listUser.value = Resource.Empty()
                        }else{
                            _listUser.value = Resource.Success(data)
                        }
                    }
                }else{
                    _listUser.value = Resource.Error("Error", null)
                }
            }

            override fun onFailure(call: Call<ListUserResponse>, t: Throwable) {
                _listUser.value = Resource.Error(t.message.toString(), null)
            }
        })
    }
}