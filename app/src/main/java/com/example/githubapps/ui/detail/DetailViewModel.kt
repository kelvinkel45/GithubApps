 package com.example.githubapps.ui.detail

import androidx.lifecycle.ViewModel
import com.example.githubapps.data.DataRepository

class DetailViewModel (private val dataRepository: DataRepository) : ViewModel() {

    fun getDetailUser(username : String) = dataRepository.getDetailUser(username)

}