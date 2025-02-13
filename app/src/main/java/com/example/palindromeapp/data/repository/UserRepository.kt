package com.example.palindromeapp.data.repository

import com.example.palindromeapp.data.remote.response.UserResponse
import com.example.palindromeapp.data.remote.retrofit.ApiConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository {
    private val apiService = ApiConfig.getApiService()

    suspend fun getUsers(page: Int): UserResponse{
        return withContext(Dispatchers.IO){
            apiService.getUsers(page)
        }
    }
}