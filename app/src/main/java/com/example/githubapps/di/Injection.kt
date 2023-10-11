package com.example.githubapps.di

import com.example.githubapps.data.DataRepository
import com.example.githubapps.data.network.ApiClient

object Injection {

    fun provideRepository(): DataRepository {
        val apiService = ApiClient.getApiService()
        return DataRepository.getInstance(apiService)
    }
}