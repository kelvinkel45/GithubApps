package com.example.githubapps.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubapps.data.DataRepository
import com.example.githubapps.di.Injection
import com.example.githubapps.ui.detail.DetailViewModel
import com.example.githubapps.ui.follow.FollowViewModel
import com.example.githubapps.ui.home.HomeViewModel

class ViewModelFactory private constructor(private val dataRepository: DataRepository) :
ViewModelProvider.NewInstanceFactory(){

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository())
            }.also { instance = it }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(dataRepository) as T
        }else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(dataRepository) as T
        }else if (modelClass.isAssignableFrom(FollowViewModel::class.java)) {
            return FollowViewModel(dataRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}