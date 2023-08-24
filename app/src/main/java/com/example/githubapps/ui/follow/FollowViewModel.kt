package com.example.githubapps.ui.follow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubapps.data.network.ApiClient
import com.example.githubapps.data.response.UserResponse
import com.example.githubapps.util.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowViewModel : ViewModel() {

    private val _listFollow = MutableLiveData<Resource<List<UserResponse>>>()
    val listFollow : LiveData<Resource<List<UserResponse>>> = _listFollow

    private var _position = 0
    private var _username = ""

    fun setPosition(position: Int){
        _position = position
    }

    fun setUsername(userName: String){
        _username = userName
    }

    fun getListFollow(){
        _listFollow.value = Resource.Loading(null)
        val client = if (_position == 1){
            ApiClient.getApiService().getListFollowing(_username)
        }else{
            ApiClient.getApiService().getListFollowers(_username)
        }
        client.enqueue(object : Callback<List<UserResponse>> {
            override fun onResponse(
                call: Call<List<UserResponse>>,
                response: Response<List<UserResponse>>
            ) {
                val data = response.body()
                if (response.isSuccessful){
                    if (data!= null){
                        if (data.isNotEmpty()){
                            _listFollow.value = Resource.Success(data)
                        }else{
                            _listFollow.value = Resource.Empty(null)
                        }
                    }
                }else{
                    _listFollow.value = Resource.Error("Error", null)
                }
            }

            override fun onFailure(call: Call<List<UserResponse>>, t: Throwable) {
                _listFollow.value = Resource.Error(t.message.toString(), null)
            }

        })
    }
}