 package com.example.githubapps.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubapps.util.Resource
import com.example.githubapps.data.network.ApiClient
import com.example.githubapps.data.response.DetailUserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {

    private val _detailUser = MutableLiveData<Resource<DetailUserResponse>>()
    val detailUser : LiveData<Resource<DetailUserResponse>> = _detailUser

    fun getDetailUser(userName : String){
        _detailUser.value = Resource.Loading(null)
        val client = ApiClient.getApiService().getDetailUser(userName)
        client.enqueue(object : Callback<DetailUserResponse>{
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                if (response.isSuccessful){
                    val data = response.body()
                    if (data != null){
                        _detailUser.value = Resource.Success(data)
                    }else{
                        _detailUser.value = Resource.Empty(null)
                    }
                }else{
                    _detailUser.value = Resource.Error("Error nih", null)
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _detailUser.value = Resource.Error(t.message.toString(), null)
            }

        })
    }

//    fun getListFollow(){
//        Resource.Loading(null)
//        val client = if (_position == 1){
//            ApiClient.getApiService().getListFollowers(_username)
//        }else{
//            ApiClient.getApiService().getListFollowing(_username)
//        }
//        client.enqueue(object : Callback<List<UserResponse>>{
//            override fun onResponse(
//                call: Call<List<UserResponse>>,
//                response: Response<List<UserResponse>>
//            ) {
//                val data = response.body()
//                if (response.isSuccessful){
//                    if (data!= null){
//                        if (data.isNotEmpty()){
//                            _listFollow.value = Resource.Success(data)
//                        }else{
//                            _listFollow.value = Resource.Empty(null)
//                        }
//                    }
//                }else{
//                    Resource.Error("Error", null)
//                }
//            }
//
//            override fun onFailure(call: Call<List<UserResponse>>, t: Throwable) {
//                Resource.Error(t.message.toString(), null)
//            }
//
//        })
//    }


}